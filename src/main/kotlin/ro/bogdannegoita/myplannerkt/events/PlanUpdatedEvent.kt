package ro.bogdannegoita.myplannerkt.events

import org.springframework.context.ApplicationEvent
import ro.bogdannegoita.myplannerkt.domain.Plan

class PlanUpdatedEvent(source: Any, val plan: Plan) : ApplicationEvent(source)
