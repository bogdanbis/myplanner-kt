package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class TaskProgressDto(
    val id: UUID? = null,
    val completed: Boolean,
    val task: TaskDto,
)
