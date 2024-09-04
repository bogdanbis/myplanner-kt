package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class PlanDto(
    val id: UUID? = null,
    var title: String,
    var description: String,
    var color: String,
    var isPublic: Boolean,
)
