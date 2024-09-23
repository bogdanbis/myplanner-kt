package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.domain.ApplicationUser
import ro.bogdannegoita.myplannerkt.domain.Plan
import java.util.*

@Component
class DomainRegistry {
    // TODO: val users = myPlannerCache<String, ApplicationUser>()
    val plans = mutableMapOf<UUID, Plan>()
    val publicPlans = mutableMapOf<UUID, Plan>()
}
