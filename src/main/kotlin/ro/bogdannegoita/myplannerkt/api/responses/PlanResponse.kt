package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Plan

class PlanResponse(plan: Plan) {
    val id = plan.id
    val title = plan.title
    val description = plan.description
    val color = plan.color
    val isPublic = plan.isPublic
    val createdAt = plan.createdAt
    val author = plan.author
}
