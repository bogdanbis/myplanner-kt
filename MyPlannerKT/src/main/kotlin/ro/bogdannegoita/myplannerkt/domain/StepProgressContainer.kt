package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressContainerDao
import java.util.*

open class StepProgressContainer(
    private val id: UUID,
    private val dao: StepProgressContainerDao,
    private val domainFactory: DomainFactory,
) {
    fun sync(stepsProgress: SortedSet<StepProgress>, steps: SortedSet<Step>): SortedSet<StepProgress> {
        steps.forEach { step ->
            if (stepsProgress.none { step.id == it.step.id })
                addStep(stepsProgress, step)
        }

        stepsProgress.forEach { stepProgress ->
            if (steps.none { it.id == stepProgress.step.id })
                removeStep(stepsProgress, stepProgress)
            else
                stepProgress.sync()
        }

        return stepsProgress.toSortedSet()
    }

    private fun addStep(stepsProgress: SortedSet<StepProgress>, step: Step) {
        val newStepProgressData = dao.addStepProgress(id, step.id)
        stepsProgress.add(domainFactory.stepProgress(newStepProgressData, step))
    }

    private fun removeStep(stepsProgress: SortedSet<StepProgress>, step: StepProgress) {
        dao.removeStepProgress(step.id)
        stepsProgress.remove(step)
    }
}
