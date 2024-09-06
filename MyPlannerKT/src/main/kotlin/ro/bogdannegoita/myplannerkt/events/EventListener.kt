package ro.bogdannegoita.myplannerkt.events

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.domain.MyPlanner

@Component
class EventListener(private val myPlanner: MyPlanner) {

    @EventListener
    fun handlePlanUpdated(event: PlanUpdatedEvent) {
        if (event.plan.isPublic)
            myPlanner.publishPlan(event.plan)
        else
            myPlanner.unpublishPlan(event.plan)
    }
}
