package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressContainerDao
import java.util.*

abstract class StepProgressContainer(
    val id: UUID,
    completed: Boolean,
    protected val parent: StepProgressContainer?,
    private val dao: StepProgressContainerDao,
    private val domainFactory: DomainFactory,
) {
    var completed: Boolean = completed
        protected set

    var steps: SortedSet<StepProgress> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }
        protected set

    fun stepChanged() {
        completed = steps.all { it.completed }
        dao.updateCompleted(id, completed)
        parent?.stepChanged()
    }

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
        stepsProgress.add(domainFactory.stepProgress(newStepProgressData, this, step))
    }

    private fun removeStep(stepsProgress: SortedSet<StepProgress>, step: StepProgress) {
        dao.removeStepProgress(step.id)
        stepsProgress.remove(step)
    }

    protected abstract fun loadSteps()
}
