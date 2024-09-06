package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class TaskDto(
    val id: UUID? = null,
    var title: String,
    var description: String,
    var index: Int,
)
