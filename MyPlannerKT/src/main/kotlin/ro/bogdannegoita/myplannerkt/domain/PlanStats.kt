package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

class PlanStats(
    private val plan: Plan,
    private val planDao: PlanDao,
) {
    val numberOfParticipants get() = planDao.getNumberOfAcquiredPlans(plan.id)
    val completedTaskCount: Int get() {
        if (plan.tasks.isEmpty())
            return 0
        var count = 0
        for (task in plan.tasks)
            count += task.completedTaskCount
        return count
    }
}
