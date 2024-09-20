package ro.bogdannegoita.myplannerkt.api.requests

import ro.bogdannegoita.myplannerkt.commons.StepDto

class PlanRequest(
    val title: String,
    val shortDescription: String = "",
    val description: String,
    val color: String,
    val isPublic: Boolean,
    val steps: List<StepDto>? = null,
)
