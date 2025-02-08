package ro.bogdannegoita.myplannerkt.api.requests

import ro.bogdannegoita.myplannerkt.commons.dtos.StepDto

class PlanRequest(
    val title: String,
    val shortDescription: String = "",
    val description: String,
    val color: String,
    val isPublic: Boolean,
    val steps: List<StepDto>? = null,
)
