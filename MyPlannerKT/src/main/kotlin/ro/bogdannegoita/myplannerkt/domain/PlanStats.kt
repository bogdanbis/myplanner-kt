package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

class PlanStats(
    private val plan: Plan,
    private val planDao: PlanDao,
) {
    val numberOfParticipants get() = planDao.getNumberOfAcquiredPlans(plan.id)
}
