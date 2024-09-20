package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Plan

class PlanResponse(plan: Plan) : PlanSimpleResponse(plan) {
    val steps = plan.steps.map(::StepResponse)
    val stats = plan.stats?.let { PlanStatsResponse(it) }
}
