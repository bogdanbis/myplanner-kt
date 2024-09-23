package ro.bogdannegoita.myplannerkt.events

import org.springframework.context.ApplicationEvent
import java.util.*

class PlanDeletedEvent(source: Any, val id: UUID) : ApplicationEvent(source)
