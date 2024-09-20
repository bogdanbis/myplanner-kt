package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.PlanStats

class PlanStatsResponse(stats: PlanStats) {
    val numberOfParticipants = stats.numberOfParticipants
    val completedStepsCount = stats.completedStepsCount
}
