package ro.bogdannegoita.myplannerkt.events

import org.springframework.context.ApplicationEvent
import ro.bogdannegoita.myplannerkt.domain.PlanInvite

class PlanInviteAcceptedEvent(
    source: Any,
    val invite: PlanInvite
) : ApplicationEvent(source)
