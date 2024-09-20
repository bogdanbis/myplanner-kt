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

    private var loadedSteps = false
    private fun loadSteps() {
        if (loadedSteps) return
        steps = dao.getSteps(id)
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
