package ro.bogdannegoita.myplannerkt.api.requests

import ro.bogdannegoita.myplannerkt.commons.dtos.StepProgressDto

class StepProgressRequest(
    val completed: Boolean,
    val comment: String? = null,
    val steps: List<StepProgressDto>? = null,
)
