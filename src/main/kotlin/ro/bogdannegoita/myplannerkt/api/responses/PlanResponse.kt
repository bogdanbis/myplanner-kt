package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Plan
import java.util.*

class PlanResponse(plan: Plan) {
    val id: UUID = plan.id
    val title: String = plan.title
    val description: String = plan.description
    val isPublic: Boolean = plan.isPublic
}
