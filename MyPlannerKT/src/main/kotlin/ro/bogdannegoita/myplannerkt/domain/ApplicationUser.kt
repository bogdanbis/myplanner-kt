package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import java.util.*

class ApplicationUser(
    data: ApplicationUserDto,
    private val dao: ApplicationUserDao,
    private val domainFactory: DomainFactory,
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

    fun createPlan(planData: PlanDto, steps: List<StepDto>? = null): Plan {
        val persistedPlanData = dao.createPlan(planData, id)
        val plan = domainFactory.plan(persistedPlanData)
        plan.update(plan.data, steps)
        createdPlans.add(plan)
        return plan
    }

    fun updatePlan(id: UUID, data: PlanDto, steps: List<StepDto>? = null): Plan? {
        val plan = createdPlans.find { it.id == id }
        plan?.update(data, steps)
        return plan
    }

    fun getAcquiredPlan(id: UUID): PlanProgress? {
        return acquiredPlans.find { it.id == id }
    }

    fun getCreatedPlan(id: UUID): Plan? {
        return createdPlans.find { it.id == id }
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

    fun deletePlan(id: UUID) {
        dao.deletePlan(id)
        createdPlans.removeIf { it.id == id }
    }

}
