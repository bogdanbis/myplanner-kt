package ro.bogdannegoita.myplannerkt.commons

import java.time.LocalDateTime
import java.util.*

data class PlanInviteDto(
    val id: UUID,
    val status: InviteStatus,
    val createdAt: LocalDateTime,
    val respondedAt: LocalDateTime? = null,
    val plan: PlanDto,
    val sender: ApplicationUserDto,
    val recipient: ApplicationUserDto? = null,
)
