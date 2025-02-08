package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.dtos.*
import ro.bogdannegoita.myplannerkt.domain.*
import ro.bogdannegoita.myplannerkt.persistence.daos.*

@Component
@Scope("prototype")
class DomainFactory(
    private val planDao: PlanDao,
    private val stepDao: StepDao,
    private val planProgressDao: PlanProgressDao,
    private val stepProgressDao: StepProgressDao,
    private val planInviteDao: PlanInviteDao,
    private val eventPublisher: ApplicationEventPublisher,
) {

    fun plan(data: PlanDto, provider: DomainProvider): Plan {
        return Plan(data, planDao, provider, eventPublisher)
    }

    fun step(data: StepDto, provider: DomainProvider): Step {
        return Step(data, stepDao, stepProgressDao, provider)
    }

    fun planProgress(data: PlanProgressDto, plan: Plan, provider: DomainProvider): PlanProgress {
        return PlanProgress(data, plan, planProgressDao, provider)
    }

    fun stepProgress(
        data: StepProgressDto,
        parent: StepProgressContainer,
        step: Step,
        provider: DomainProvider
    ): StepProgress {
        return StepProgress(data, step, parent, stepProgressDao, provider)
    }

    fun planStats(plan: Plan): PlanStats {
        return PlanStats(plan, planDao)
    }

    fun planInvite(data: PlanInviteDto, provider: DomainProvider): PlanInvite {
        return PlanInvite(data, planInviteDao, provider)
    }
}
