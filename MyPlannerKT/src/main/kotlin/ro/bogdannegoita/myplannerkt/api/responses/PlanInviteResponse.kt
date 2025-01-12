package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.PlanInvite

class PlanInviteResponse(planInvite: PlanInvite) {
    val id = planInvite.id
    val status = planInvite.status
    val createdAt = planInvite.createdAt
    val respondedAt = planInvite.respondedAt
    val plan = PlanSimpleResponse(planInvite.plan.value)
    val sender = ApplicationUserResponse(planInvite.sender.value)
    val recipient = planInvite.recipient.value?.let { ApplicationUserResponse(it) }
}
