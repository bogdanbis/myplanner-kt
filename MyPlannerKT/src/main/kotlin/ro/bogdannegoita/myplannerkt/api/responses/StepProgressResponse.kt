package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.StepProgress

class StepProgressResponse(stepProgress: StepProgress) {
    val id = stepProgress.id
    val completed = stepProgress.completed
    val comment = stepProgress.comment
    val step = StepResponse(stepProgress.step)
    val steps = stepProgress.steps.map(::StepProgressResponse)
    val completedStepsCount = stepProgress.completedStepsCount
    val totalStepsCount = stepProgress.totalStepsCount
}
