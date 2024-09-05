package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.domain.Plan
import ro.bogdannegoita.myplannerkt.domain.PlanProgress
import ro.bogdannegoita.myplannerkt.domain.Task
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

@Component
@Scope("prototype")
class DomainFactory(
    private val planDao: PlanDao,
    private val eventPublisher: ApplicationEventPublisher,
) {
    val registry = DomainRegistry()

    fun plan(data: PlanDto): Plan {
        if (registry.plans[data.id] != null)
            return registry.plans[data.id]!!
        val plan = Plan(data, planDao, this, eventPublisher)
        registry.plans[plan.id] = plan
        return plan
    }

    fun task(data: TaskDto): Task {
        return Task(data)
    }

    fun planProgress(data: PlanProgressDto, plan: Plan): PlanProgress {
        return PlanProgress(data, plan)
    }
}
