package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class PlanDto(
    val id: UUID? = null,
    val title: String,
    val description: String,
    val isPublic: Boolean,
)
