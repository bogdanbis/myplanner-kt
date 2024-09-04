package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.Plan
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

@Component
@Scope("prototype")
class DomainFactory(
    private val planDao: PlanDao,
) {
    val registry = DomainRegistry()

    fun plan(data: PlanDto): Plan {
        return Plan(data, planDao, this)
    }
}
