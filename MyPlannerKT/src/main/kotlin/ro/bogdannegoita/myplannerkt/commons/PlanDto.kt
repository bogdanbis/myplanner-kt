package ro.bogdannegoita.myplannerkt.commons

import java.time.LocalDateTime
import java.util.*

data class PlanDto(
    val id: UUID? = null,
    var title: String,
    var shortDescription: String,
    var description: String,
    var color: String,
    var isPublic: Boolean,
    val createdAt: LocalDateTime,
    var lastModifiedAt: LocalDateTime,
    val steps: List<StepDto>? = null,
)
