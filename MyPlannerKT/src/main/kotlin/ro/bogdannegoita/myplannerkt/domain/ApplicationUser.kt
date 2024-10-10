package ro.bogdannegoita.myplannerkt.domain

import org.springframework.context.ApplicationEventPublisher
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.events.PlanDeletedEvent
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import java.util.*

class ApplicationUser(
    val data: ApplicationUserDto,
    private val dao: ApplicationUserDao,
    private val domainFactory: DomainFactory,
    private val eventPublisher: ApplicationEventPublisher,
) {
    val id: UUID = data.id!!
    val email: String = data.email
    val firstName: String = data.firstName
    val lastName: String = data.lastName

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

    fun acquirePlan(plan: Plan): PlanProgress? {
        if (acquiredPlans.any { it.plan.id == plan.id })
            return null
        val planProgressData = dao.acquirePlan(id, plan.id)
        val planProgress = domainFactory.planProgress(planProgressData, plan)
        acquiredPlans.add(planProgress)
        return planProgress
    }

    fun createPlan(planData: PlanDto): Plan {
        val persistedPlanData = dao.createPlan(planData, id)
        val plan = domainFactory.plan(persistedPlanData)
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

    private var loadedAcquiredPlans = false
    private fun loadAcquiredPlans() {
        if (loadedAcquiredPlans)
            return
        acquiredPlans = dao.getAcquiredPlans(id)
            .map { domainFactory.planProgress(it, domainFactory.plan(it.plan)) }
            .toSortedSet()
        loadedAcquiredPlans = true
    }

    private var loadedCreatedPlans = false
    private fun loadCreatedPlans() {
        if (loadedCreatedPlans)
            return
        createdPlans = dao.getCreatedPlans(id).map { domainFactory.plan(it) }
            .toSortedSet()
        loadedCreatedPlans = true
    }
}
