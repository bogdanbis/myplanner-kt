package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.PlanProgress

class ParticipantProgressResponse(planProgress: PlanProgress) {
    val id = planProgress.id
    val participant = ApplicationUserResponse(planProgress.user.value)
    val acquiredAt = planProgress.acquiredAt
    val lastSyncedPlan = planProgress.lastSyncedPlan
    val lastActive = planProgress.lastActive
    val completed = planProgress.completed
    val steps = planProgress.steps.map(::StepProgressResponse)
    val completedStepsCount = planProgress.completedStepsCount
    val totalStepsCount = planProgress.totalStepsCount
}
