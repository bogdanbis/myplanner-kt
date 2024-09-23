package ro.bogdannegoita.myplannerkt.domain

import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.domain.factories.myPlannerCache
import ro.bogdannegoita.myplannerkt.events.PlanDeletedEvent
import ro.bogdannegoita.myplannerkt.events.PlanUpdatedEvent
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.time.LocalDateTime
import java.util.*

@Component
class MyPlanner(
    private val domainProvider: ObjectProvider<ApplicationUser>,
    private val userDao: ApplicationUserDao,
    private val planDao: PlanDao,
    private val domainFactory: DomainFactory,
) {

    private val users = myPlannerCache<String, ApplicationUser>()
    private val publicPlansRegistry = mutableMapOf<UUID, Plan>()

    fun loadUser(email: String): ApplicationUser {
        var user = users.getOrNull(email)
        if (user != null)
            return user
        val userData = userDao.findByEmail(email)
        user = domainProvider.getObject(userData)
        users[email] = user
        return user
    }

    fun getPublicPlans(): List<Plan> {
        loadPublicPlans()
        return publicPlansRegistry.values.toList()
    }

    fun getPublicPlan(id: UUID): Plan? {
        loadPlan(id)
        return publicPlansRegistry[id]
    }

    fun getPlan(user: ApplicationUser, id: UUID): Plan? {
        return getPublicPlan(id) ?: user.getCreatedPlan(id)
    }

    fun createPlan(user: ApplicationUser, planData: PlanDto, steps: List<StepDto>? = null): Plan {
        planData.lastModifiedAt = LocalDateTime.now()
        val plan = user.createPlan(planData, steps)
        if (plan.isPublic)
            publishPlan(plan)
        return plan
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
                val plan = domainFactory.plan(it)
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
            publicPlansRegistry[id] = domainFactory.plan(planDto)
    }

}
