package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.PlanProgress

class PlanProgressResponse(planProgress: PlanProgress) {
    val id = planProgress.id
    val participant = ApplicationUserResponse(planProgress.user.value)
    val plan = PlanResponse(planProgress.plan)
    val acquiredAt = planProgress.acquiredAt
    val lastSyncedPlan = planProgress.lastSyncedPlan
    val completed = planProgress.completed
    val comment = planProgress.comment
    val steps = planProgress.steps.map(::StepProgressResponse)
    val completedStepsCount = planProgress.completedStepsCount
    val totalStepsCount = planProgress.totalStepsCount
}
