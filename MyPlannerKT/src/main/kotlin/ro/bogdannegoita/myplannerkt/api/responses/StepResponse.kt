package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Step

class StepResponse(step: Step) : StepSimpleResponse(step) {
    val steps = step.steps.map(::StepResponse)
}
