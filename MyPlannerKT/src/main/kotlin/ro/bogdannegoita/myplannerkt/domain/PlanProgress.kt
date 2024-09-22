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
) : Comparable<PlanProgress> {
    val id = data.id!!
    val acquiredAt by data::acquiredAt
    var lastSyncedPlan by data::lastSyncedPlan

    var steps: SortedSet<StepProgress> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }
        private set

    fun updateStepProgress(stepId: UUID, stepProgressData: StepProgressDto): StepProgress? {
        return steps.find { it.id == stepId }
            ?.update(stepProgressData)
    }

    fun sync() {
        // delete steps that are not present anymore
        steps.filter { stepProgress -> plan.steps.none { it.id == stepProgress.step.id } }
            .forEach {
                dao.deleteStep(it.id)
                steps.remove(it)
            }

        // add new steps
        plan.steps.forEach { step ->
            if (steps.none { step.id == it.step.id }) {
                val newStepProgressData = dao.createStepProgress(step.id, id)
                steps.add(domainFactory.stepProgress(newStepProgressData, step))
            }
        }

        lastSyncedPlan = plan.lastModifiedAt
        dao.update(id, data)
    }

    private var loadedSteps = false
    private fun loadSteps() {
        if (loadedSteps) return
        steps = dao.getSteps(id)
            .filter { dto -> plan.steps.any { it.id == dto.step!!.id } }
            .map { dto ->
                val step = plan.steps.find { it.id == dto.step!!.id }
                val stepProgress = domainFactory.stepProgress(dto, step!!)
                stepProgress
            }
            .toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: PlanProgress) = other.acquiredAt.compareTo(acquiredAt)
}
