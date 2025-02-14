package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Plan

class PlanWithStepsResponse(plan: Plan) : PlanResponse(plan) {
    val steps = plan.steps.map(::StepResponse)
    val stats = plan.stats?.let { PlanStatsResponse(it) }
}
