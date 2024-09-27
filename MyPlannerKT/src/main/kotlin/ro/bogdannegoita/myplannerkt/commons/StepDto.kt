package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class StepDto(
    val id: UUID? = null,
    var title: String,
    var description: String,
    var index: Int,
    var completed: Boolean = false,
    val steps: List<StepDto>? = null,
)
