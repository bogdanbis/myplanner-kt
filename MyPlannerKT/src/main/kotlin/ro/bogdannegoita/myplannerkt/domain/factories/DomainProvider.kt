package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import ro.bogdannegoita.myplannerkt.domain.Plan
import ro.bogdannegoita.myplannerkt.domain.PlanProgress
import ro.bogdannegoita.myplannerkt.domain.Step
import ro.bogdannegoita.myplannerkt.domain.StepProgressContainer

@Component
@Scope("prototype")
class DomainProvider(
    private val factory: DomainFactory,
    private val registry: DomainRegistry,
) {

    fun plan(data: PlanDto): Plan {
        var plan = registry.plans.getOrNull(data.id!!)
        if (plan != null)
            return plan
        plan = factory.plan(data, this)
        registry.plans[plan.id] = plan
        return plan
    }

    fun planProgress(data: PlanProgressDto, plan: Plan): PlanProgress {
        var planProgress = registry.planProgress.getOrNull(data.id!!)
        if (planProgress != null)
            return planProgress
        planProgress = factory.planProgress(data, plan, this)
        registry.planProgress[planProgress.id] = planProgress
        return planProgress
    }

    fun step(data: StepDto) = factory.step(data, this)

    fun stepProgress(data: StepProgressDto, parent: StepProgressContainer, step: Step) =
        factory.stepProgress(data, parent, step, this)

    fun planStats(plan: Plan) = factory.planStats(plan)
}
