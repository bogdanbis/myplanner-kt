package ro.bogdannegoita.myplannerkt.commons

import java.time.LocalDateTime
import java.util.*

data class PlanProgressDto(
    val id: UUID? = null,
    val plan: PlanDto,
    val acquiredAt: LocalDateTime,
)
