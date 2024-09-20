package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

class PlanStats(
    private val plan: Plan,
    private val planDao: PlanDao,
) {
    val numberOfParticipants get() = planDao.getNumberOfAcquiredPlans(plan.id)
    val completedStepsCount: Int get() {
        if (plan.steps.isEmpty())
            return 0
        var count = 0
        for (step in plan.steps)
            count += step.completedStepsCount
        return count
    }
}
