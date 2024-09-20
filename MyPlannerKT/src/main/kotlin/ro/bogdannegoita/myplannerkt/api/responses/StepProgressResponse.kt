package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.StepProgress

class StepProgressResponse(stepProgress: StepProgress) {
    val id = stepProgress.id
    val completed = stepProgress.completed
    val step = StepResponse(stepProgress.step)
}
