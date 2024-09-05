package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.PlanProgress

class PlanProgressResponse(planProgress: PlanProgress) {
    val id = planProgress.id
    val plan = planProgress.planData
    val acquiredAt = planProgress.acquiredAt
}
