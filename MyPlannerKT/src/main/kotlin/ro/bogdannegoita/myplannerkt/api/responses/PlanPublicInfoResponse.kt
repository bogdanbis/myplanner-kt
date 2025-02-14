package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Plan
import kotlin.math.min

class PlanPublicInfoResponse(plan: Plan) {
    val id = plan.id
    val title = plan.title
    val shortDescription = plan.shortDescription
    val description =
        if (plan.description.length > 200) plan.description.substring(0, 200) + "..."
        else plan.description
    val color = plan.color
    val isPublic = plan.isPublic
    val createdAt = plan.createdAt
    val lastModifiedAt = plan.lastModifiedAt
    val author = ApplicationUserResponse(plan.author!!)
    val numberOfParticipants = plan.stats?.numberOfParticipants
    val images = plan.images
    val steps = plan.steps.toList().slice(IntRange(0, min(1, plan.steps.size - 1))).map(::StepResponse)
    val totalSteps = plan.steps.size
}
