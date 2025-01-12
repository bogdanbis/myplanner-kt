package ro.bogdannegoita.myplannerkt.events

import org.springframework.context.ApplicationEvent
import ro.bogdannegoita.myplannerkt.domain.PlanInvite

class PlanInviteSentEvent(
    source: Any,
    val invite: PlanInvite,
    val recipientEmail: String
) : ApplicationEvent(source)
