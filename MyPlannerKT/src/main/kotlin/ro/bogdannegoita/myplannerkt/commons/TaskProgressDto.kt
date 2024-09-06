package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class TaskProgressDto(
    val id: UUID? = null,
    var completed: Boolean,
    val task: TaskDto?,
)
