package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class StepProgressDto(
    val id: UUID? = null,
    var completed: Boolean,
    val step: StepDto?,
)
