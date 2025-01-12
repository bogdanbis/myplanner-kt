package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanInviteDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanInviteDao
import java.util.*

class PlanInvite(
    private val data: PlanInviteDto,
    private val dao: PlanInviteDao,
    private val domainProvider: DomainProvider,
) : Comparable<PlanInvite> {
    val id: UUID = data.id
    val status by data::status
    val createdAt by data::createdAt
    val respondedAt by data::respondedAt

    val plan: Lazy<Plan> = lazy { domainProvider.plan(dao.getPlan(id)) }
    val sender: Lazy<ApplicationUserDto> = lazy { dao.getSender(id) }
    val recipient: Lazy<ApplicationUserDto> = lazy { dao.getRecipient(id) }

    override fun compareTo(other: PlanInvite) = createdAt.compareTo(other.createdAt)
}
