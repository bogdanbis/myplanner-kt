package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Step

class StepResponse(step: Step) {
    val id = step.id
    val title = step.title
    val description = step.description
    val index = step.index
    val steps = step.steps.map(::StepResponse)
}
