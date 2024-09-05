package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
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

    var acquiredPlans: SortedSet<Plan> = sortedSetOf()
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

    fun acquirePlan(plan: Plan) {
        if (acquiredPlans.contains(plan))
            return
        acquiredPlans.add(plan)
        dao.acquirePlan(id, plan.id)
    }

    fun createPlan(planData: PlanDto): Plan {
        val persistedPlanData = dao.createPlan(planData, id)
        val plan = domainFactory.plan(persistedPlanData)
        return plan
    }

    fun updatePlan(id: UUID, data: PlanDto): Plan {
        val plan = createdPlans.find { it.id == id }
        if (plan == null)
            throw EntityNotFoundException(Plan::class)
        plan.update(data)
        return plan
    }

    fun getCreatedPlan(id: UUID): Plan? {
        return createdPlans.find { it.id == id }
    }

    private var loadedAcquiredPlans = false
    private fun loadAcquiredPlans() {
        if (loadedAcquiredPlans)
            return
        acquiredPlans = dao.getAcquiredPlans(id).map { domainFactory.plan(it) }
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
