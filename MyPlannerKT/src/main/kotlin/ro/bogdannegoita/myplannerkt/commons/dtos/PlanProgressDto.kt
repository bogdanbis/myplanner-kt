package ro.bogdannegoita.myplannerkt.commons.dtos

import java.time.LocalDateTime
import java.util.*

data class PlanProgressDto(
    val id: UUID? = null,
    val plan: PlanDto,
    val acquiredAt: LocalDateTime,
    var lastSyncedPlan: LocalDateTime,
    var lastActive: LocalDateTime,
    var completed: Boolean,
    var comment: String?,
)
