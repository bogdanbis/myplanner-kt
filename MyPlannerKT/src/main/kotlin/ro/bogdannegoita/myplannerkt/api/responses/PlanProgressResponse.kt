package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.PlanProgress

class PlanProgressResponse(planProgress: PlanProgress) {
    val id = planProgress.id
    val plan = PlanSimpleResponse(planProgress.plan)
    val acquiredAt = planProgress.acquiredAt
    val lastSyncedPlan = planProgress.lastSyncedPlan
    val completed = planProgress.completed
    val comment = planProgress.comment
    val steps = planProgress.steps.map(::StepProgressResponse)
}
