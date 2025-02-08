package ro.bogdannegoita.myplannerkt.commons.dtos

import ro.bogdannegoita.myplannerkt.commons.types.InviteStatus
import java.time.LocalDateTime
import java.util.*

data class PlanInviteDto(
    val id: UUID,
    var status: InviteStatus,
    val createdAt: LocalDateTime,
    var respondedAt: LocalDateTime? = null,
    val plan: PlanDto,
    val sender: ApplicationUserDto,
    val recipient: ApplicationUserDto? = null,
)
