package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class TaskDto(
    val id: UUID? = null,
    val title: String,
    val description: String,
    val index: Int,
)
