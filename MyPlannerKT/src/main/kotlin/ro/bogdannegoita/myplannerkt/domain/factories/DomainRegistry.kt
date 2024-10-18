package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.domain.Plan
import ro.bogdannegoita.myplannerkt.domain.PlanProgress
import java.util.*

@Component
@Scope("prototype")
class DomainRegistry {
    val plans = myPlannerCache<UUID, Plan>()
    val planProgress = myPlannerCache<UUID, PlanProgress>()
}
