package ro.bogdannegoita.myplannerkt.domain

import org.springframework.context.ApplicationEventPublisher
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.InviteStatus
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.types.UserUIPreferences
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.events.PlanDeletedEvent
import ro.bogdannegoita.myplannerkt.events.PlanInviteSentEvent
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import java.util.*

class ApplicationUser(
    val data: ApplicationUserDto,
    private val dao: ApplicationUserDao,
    private val domainProvider: DomainProvider,
    private val eventPublisher: ApplicationEventPublisher,
) {
    val id: UUID = data.id!!
    val email: String = data.email
    val firstName: String = data.firstName
    val lastName: String = data.lastName
    var uiPreferences by data::uiPreferences
        private set

    var acquiredPlans: SortedSet<PlanProgress> = sortedSetOf()
        get() {
            loadAcquiredPlans()
            return field
        }
        private set

    var createdPlans: SortedSet<Plan> = sortedSetOf()
        get() {
            loadCreatedPlans()
            return field
        }
        private set

    var sentInvites: SortedSet<PlanInvite> = sortedSetOf()
        get() {
            loadSentInvites()
            return field
        }
        private set

    var receivedInvites: SortedSet<PlanInvite> = sortedSetOf()
        get() {
            loadReceivedInvites()
            return field
        }
        private set

    fun acquirePlan(plan: Plan): PlanProgress? {
        if (acquiredPlans.any { it.plan.id == plan.id })
            return null
        val planProgressData = dao.acquirePlan(id, plan.id)
        val planProgress = domainProvider.planProgress(planProgressData, plan)
        plan.acquired()
        acquiredPlans.add(planProgress)
        return planProgress
    }

    fun createPlan(planData: PlanDto): Plan {
        val persistedPlanData = dao.createPlan(planData, id)
        val plan = domainProvider.plan(persistedPlanData)
        createdPlans.add(plan)
        return plan
    }

    fun updatePlan(id: UUID, data: PlanDto): Plan? {
        val plan = createdPlans.find { it.id == id }
        plan?.update(data)
        return plan
    }

    fun getAcquiredPlan(id: UUID): PlanProgress? {
        return acquiredPlans.find { it.id == id }
    }

    fun getCreatedPlan(id: UUID): Plan? {
        return createdPlans.find { it.id == id }
    }

    fun deletePlan(id: UUID) {
        dao.deletePlan(id)
        createdPlans.removeIf { it.id == id }
        eventPublisher.publishEvent(PlanDeletedEvent(this, id))
    }

    fun updateUIPreferences(value: UserUIPreferences) {
        dao.updateUIPreferences(id, value)
        uiPreferences = value
    }

    fun inviteUser(planId: UUID, recipientEmail: String): PlanInvite? {
        if (inviteAlreadySent(planId, recipientEmail)
                || recipientHasPlan(recipientEmail, planId)
                || recipientEmail == email)
            return null
        val persistedData = dao.inviteUser(planId, email, recipientEmail)
        val invite = domainProvider.planInvite(persistedData)
        sentInvites.add(invite)
        eventPublisher.publishEvent(PlanInviteSentEvent(this, invite, recipientEmail))
        return invite
    }

    private fun inviteAlreadySent(planId: UUID, recipientEmail: String): Boolean {
        val invite = sentInvites.find { it.plan.value.id == planId && it.recipient.value.email == recipientEmail }
        return invite != null && invite.status == InviteStatus.PENDING
    }

    private fun recipientHasPlan(recipientEmail: String, planId: UUID): Boolean {
        return dao.userHasPlan(recipientEmail, planId)
    }

    private var loadedAcquiredPlans = false
    private fun loadAcquiredPlans() {
        if (loadedAcquiredPlans)
            return
        acquiredPlans = dao.getAcquiredPlans(id)
            .map { domainProvider.planProgress(it, domainProvider.plan(it.plan)) }
            .toSortedSet()
        loadedAcquiredPlans = true
    }

    private var loadedCreatedPlans = false
    private fun loadCreatedPlans() {
        if (loadedCreatedPlans)
            return
        createdPlans = dao.getCreatedPlans(id).map { domainProvider.plan(it) }
            .toSortedSet()
        loadedCreatedPlans = true
    }

    private var loadedSentInvites = false
    private fun loadSentInvites() {
        if (loadedSentInvites)
            return
        sentInvites = dao.getSentInvites(id)
            .map { domainProvider.planInvite(it) }
            .toSortedSet()
        loadedSentInvites = true
    }

    private var loadedReceivedInvites = false
    private fun loadReceivedInvites() {
        if (loadedReceivedInvites)
            return
        receivedInvites = dao.getReceivedInvites(id)
            .map { domainProvider.planInvite(it) }
            .toSortedSet()
        loadedReceivedInvites = true
    }
}
