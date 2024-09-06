package ro.bogdannegoita.myplannerkt.domain.factories

import ro.bogdannegoita.myplannerkt.domain.Plan
import java.util.*

class DomainRegistry {
    val plans = mutableMapOf<UUID, Plan>()
    val publicPlans = mutableMapOf<UUID, Plan>()
}
