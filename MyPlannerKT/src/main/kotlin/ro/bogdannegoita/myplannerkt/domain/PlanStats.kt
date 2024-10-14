package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

class PlanStats(
    private val plan: Plan,
    private val planDao: PlanDao,
) {
    var numberOfParticipants: Int = 0
        get() {
            loadNumberOfParticipants()
            return field
        }

    val completedStepsCount: Int
        get() {
            if (plan.steps.isEmpty())
                return 0
            var count = 0
            for (step in plan.steps)
                count += step.completedStepsCount
            return count
        }

    private var loadedNumberOfParticipants = false
    private fun loadNumberOfParticipants() {
        if (loadedNumberOfParticipants)
            return
        numberOfParticipants = planDao.getNumberOfAcquiredPlans(plan.id)
        loadedNumberOfParticipants = true
    }
}
