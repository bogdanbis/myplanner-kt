package ro.bogdannegoita.myplannerkt.commons.dtos

import java.util.*

data class StepProgressDto(
    val id: UUID? = null,
    var completed: Boolean,
    var comment: String?,
    val step: StepDto? = null,
    val steps: List<StepProgressDto>? = null,
)
