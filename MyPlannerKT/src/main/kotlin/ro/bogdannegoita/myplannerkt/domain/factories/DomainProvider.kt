package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.dtos.*
import ro.bogdannegoita.myplannerkt.domain.*

@Component
@Scope("prototype")
class DomainProvider(
    private val factory: DomainFactory,
    private val registry: DomainRegistry,
) {

    fun plan(data: PlanDto, shortLived: Boolean = false): Plan {
        var plan = registry.plans.getOrNull(data.id!!)
        if (plan != null)
            return plan
        plan = factory.plan(data, this)
        if (!shortLived)
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

    fun planInvite(data: PlanInviteDto): PlanInvite {
        var invite = registry.planInvites.getOrNull(data.id)
        if (invite != null)
            return invite
        invite = factory.planInvite(data, this)
        registry.planInvites[invite.id] = invite
        return invite
    }
}
