package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressContainerDao
import java.util.*

abstract class StepProgressContainer(
    val id: UUID,
    completed: Boolean,
    protected val parent: StepProgressContainer?,
    private val dao: StepProgressContainerDao,
    private val domainFactory: DomainProvider,
) {
    var completed: Boolean = completed
        protected set

    var steps: SortedSet<StepProgress> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }
        protected set

    open fun stepChanged() {
        if (completed == steps.all { it.completed })
            return
        completed = steps.all { it.completed }
        dao.updateCompleted(id, completed)
        parent?.stepChanged()
    }

    fun sync(stepsProgress: SortedSet<StepProgress>, steps: SortedSet<Step>): SortedSet<StepProgress> {
        stepsProgress.filter { sp -> steps.none { it.id == sp.step.id } }
            .forEach { sp -> removeStepProgress(stepsProgress, sp) }

        steps.forEach { step ->
            val existingStepProgress = stepsProgress.find { it.step.id == step.id }
            if (existingStepProgress != null)
                existingStepProgress.sync()
            else
                addStep(stepsProgress, step)
        }

        return stepsProgress.toSortedSet()
    }

    private fun addStep(stepsProgress: SortedSet<StepProgress>, step: Step) {
        val newStepProgressData = dao.addStepProgress(id, step.id)
        stepsProgress.add(domainFactory.stepProgress(newStepProgressData, this, step))
    }

    private fun removeStepProgress(stepsProgress: SortedSet<StepProgress>, step: StepProgress) {
        dao.removeStepProgress(step.id)
        stepsProgress.remove(step)
    }

    protected abstract fun loadSteps()
}
