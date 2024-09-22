package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.domain.Plan
import java.util.*

@Component
class DomainRegistry {
    val plans = mutableMapOf<UUID, Plan>()
    val publicPlans = mutableMapOf<UUID, Plan>()
}
