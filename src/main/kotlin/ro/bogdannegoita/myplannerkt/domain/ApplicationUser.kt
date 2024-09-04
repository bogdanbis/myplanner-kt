package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.util.*

class ApplicationUser(
    data: ApplicationUserDto,
    private val dao: ApplicationUserDao,
    private val planDao: PlanDao,
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
