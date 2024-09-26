package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanProgressDao
import java.util.*

class PlanProgress(
    private val data: PlanProgressDto,
    val plan: Plan,
    private val dao: PlanProgressDao,
    private val domainFactory: DomainFactory,
) : StepProgressContainer(data.id!!, dao, domainFactory), Comparable<PlanProgress> {
    val id = data.id!!
    val acquiredAt by data::acquiredAt
    var lastSyncedPlan by data::lastSyncedPlan

    var steps: SortedSet<StepProgress> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }
        private set

    private val stepProgressRegistry by domainFactory::stepProgressRegistry

    fun updateStepProgress(stepId: UUID, stepProgressData: StepProgressDto): StepProgress? {
        return getStep(stepId)?.update(stepProgressData)
    }

    fun sync() {
        steps = sync(steps, plan.steps)
        lastSyncedPlan = plan.lastModifiedAt
        dao.update(id, data)
    }

    private fun getStep(id: UUID): StepProgress? {
        if (stepProgressRegistry[id] != null)
            return stepProgressRegistry[id]
        for (step in steps) {
            if (step.id == id)
                return step
            val substep = step.steps.find { it.id == id }
            if (substep != null)
                return substep
        }
        return null
    }

    private var loadedSteps = false
    private fun loadSteps() {
        if (loadedSteps) return
        steps = dao.getSteps(id)
            .filter { dto -> plan.steps.any { it.id == dto.step!!.id } }
            .map { dto ->
                val step = plan.steps.find { it.id == dto.step!!.id }
                val stepProgress = domainFactory.stepProgress(dto, step!!)
                stepProgressRegistry[stepProgress.id] = stepProgress
                stepProgress
            }
            .toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: PlanProgress) = other.acquiredAt.compareTo(acquiredAt)
}
