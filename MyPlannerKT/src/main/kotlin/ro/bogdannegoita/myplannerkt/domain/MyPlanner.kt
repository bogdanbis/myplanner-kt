package ro.bogdannegoita.myplannerkt.domain

import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.dtos.PlanDto
import ro.bogdannegoita.myplannerkt.commons.types.Photo
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.domain.factories.myPlannerCache
import ro.bogdannegoita.myplannerkt.events.PlanDeletedEvent
import ro.bogdannegoita.myplannerkt.events.PlanInviteAcceptedEvent
import ro.bogdannegoita.myplannerkt.events.PlanInviteSentEvent
import ro.bogdannegoita.myplannerkt.events.PlanUpdatedEvent
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PhotoDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.time.LocalDateTime
import java.util.*

@Component
class MyPlanner(
    private val userBeanProvider: ObjectProvider<ApplicationUser>,
    private val userDao: ApplicationUserDao,
    private val planDao: PlanDao,
    private val photoDao: PhotoDao,
    private val domainProvider: DomainProvider,
) {

    private val users = myPlannerCache<String, ApplicationUser>()
    private val publicPlansRegistry = mutableMapOf<UUID, Plan>()

    fun loadUser(email: String): ApplicationUser {
        var user = users.getOrNull(email)
        if (user != null)
            return user
        val userData = userDao.findByEmail(email)
        user = userBeanProvider.getObject(userData)
        users[email] = user
        return user
    }

    fun getPublicPlans(): List<Plan> {
        loadPublicPlans()
        return publicPlansRegistry.values.toList()
    }

    fun findByTitle(title: String): List<Plan> {
        return planDao.findByTitle(title)
            .map { domainProvider.plan(it, shortLived = true) }
    }

    fun getPublicPlan(id: UUID): Plan? {
        loadPlan(id)
        return publicPlansRegistry[id]
    }

    fun createPlan(user: ApplicationUser, planData: PlanDto): Plan {
        planData.lastModifiedAt = LocalDateTime.now()
        val plan = user.createPlan(planData)
        if (plan.isPublic)
            publishPlan(plan)
        return plan
    }

    fun getImage(id: UUID): Photo {
        return photoDao.findById(id)
    }

    @EventListener
    fun handlePlanUpdated(event: PlanUpdatedEvent) {
        if (event.plan.isPublic)
            publishPlan(event.plan)
        else
            unpublishPlan(event.plan)
    }

    @EventListener
    fun handlePlanDeleted(event: PlanDeletedEvent) {
        publicPlansRegistry.remove(event.id)
    }

    @EventListener
    fun handlePlanInviteSent(event: PlanInviteSentEvent) {
        val recipient = users.getOrNull(event.recipientEmail)
            ?: return
        recipient.receivedInvites.add(event.invite)
    }

    @EventListener
    fun handlePlanInviteAccepted(event: PlanInviteAcceptedEvent) {
        val recipient = loadUser(event.invite.recipient.value.email)
        recipient.acquirePlan(event.invite.plan.value)
    }

    private fun publishPlan(plan: Plan) {
        publicPlansRegistry[plan.id] = plan
    }

    private fun unpublishPlan(plan: Plan) {
        publicPlansRegistry.remove(plan.id)
    }

    private var loadedPublicPlans = false
    private fun loadPublicPlans() {
        if (loadedPublicPlans) return
        planDao.getPublicPlans()
            .map {
                val plan = domainProvider.plan(it)
                publicPlansRegistry[plan.id] = plan
                plan
            }
            .toMutableList()
        loadedPublicPlans = true
    }

    private fun loadPlan(id: UUID) {
        if (publicPlansRegistry[id] != null)
            return
        val planDto = planDao.getById(id)
        if (planDto.isPublic)
            publicPlansRegistry[id] = domainProvider.plan(planDto)
    }

}
