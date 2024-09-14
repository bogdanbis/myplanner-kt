package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Plan

class PlanResponse(plan: Plan) : PlanSimpleResponse(plan) {
    val tasks = plan.tasks.map(::TaskResponse)
    val stats = plan.stats?.let { PlanStatsResponse(it) }
}
