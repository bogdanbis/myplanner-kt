package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import ro.bogdannegoita.myplannerkt.domain.*
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanProgressDao
import ro.bogdannegoita.myplannerkt.persistence.daos.StepDao
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressDao
import java.util.UUID

@Component
@Scope("prototype")
class DomainFactory(
    private val planDao: PlanDao,
    private val stepDao: StepDao,
    private val planProgressDao: PlanProgressDao,
    private val stepProgressDao: StepProgressDao,
    private val eventPublisher: ApplicationEventPublisher,
    private val registry: DomainRegistry,
) {

    val stepProgressRegistry: MutableMap<UUID, StepProgress> = mutableMapOf()

    fun plan(data: PlanDto): Plan {
        var plan = registry.plans.getOrNull(data.id!!)
        if (plan != null)
            return plan
        plan = Plan(data, planDao, this, eventPublisher)
        registry.plans[plan.id] = plan
        return plan
    }

    fun step(data: StepDto): Step {
        return Step(data, stepDao, stepProgressDao, this)
    }

    fun planProgress(data: PlanProgressDto, plan: Plan): PlanProgress {
        return PlanProgress(data, plan, planProgressDao, this)
    }

    fun stepProgress(data: StepProgressDto, step: Step): StepProgress {
        return StepProgress(data, step, stepProgressDao, this)
    }

    fun planStats(plan: Plan): PlanStats {
        return PlanStats(plan, planDao)
    }
}
