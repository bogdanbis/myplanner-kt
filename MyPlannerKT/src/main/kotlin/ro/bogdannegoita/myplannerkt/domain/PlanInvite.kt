package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.dtos.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.types.InviteStatus
import ro.bogdannegoita.myplannerkt.commons.types.InviteStatus.ACCEPTED
import ro.bogdannegoita.myplannerkt.commons.types.InviteStatus.DECLINED
import ro.bogdannegoita.myplannerkt.commons.dtos.PlanInviteDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanInviteDao
import java.time.LocalDateTime
import java.util.*

class PlanInvite(
    private val data: PlanInviteDto,
    private val dao: PlanInviteDao,
    private val domainProvider: DomainProvider,
) : Comparable<PlanInvite> {
    val id: UUID = data.id
    var status by data::status
        private set
    val createdAt by data::createdAt
    var respondedAt by data::respondedAt
        private set

    val plan: Lazy<Plan> = lazy { domainProvider.plan(dao.getPlan(id)) }
    val sender: Lazy<ApplicationUserDto> = lazy { dao.getSender(id) }
    val recipient: Lazy<ApplicationUserDto> = lazy { dao.getRecipient(id) }

    fun accept() {
        updateStatus(ACCEPTED)
    }

    fun decline() {
        updateStatus(DECLINED)
    }

    private fun updateStatus(value: InviteStatus) {
        status = value
        respondedAt = LocalDateTime.now()
        dao.updateStatus(id, status, respondedAt!!)
    }

    override fun compareTo(other: PlanInvite) = createdAt.compareTo(other.createdAt)
}
