package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.persistence.daos.StepContainerDao
import java.util.*

open class StepContainer(
    private val id: UUID,
    private val dao: StepContainerDao,
    private val domainProvider: DomainProvider,
) {
    protected fun updateSteps(steps: SortedSet<Step>, newSteps: List<StepDto>) {
        steps.filter { step -> newSteps.none { it.id == step.id } }
            .forEach { step -> removeStep(steps, step) }

        newSteps.forEach { step ->
            val existingStep = steps.find { it.id == step.id }
            if (existingStep != null)
                existingStep.update(step)
            else
                addStep(steps, step)
        }
    }

    private fun addStep(steps: SortedSet<Step>, stepData: StepDto) {
        val persistedData = dao.addStep(id, stepData)
        val step = domainProvider.step(persistedData)
        steps.add(step)
    }

    private fun removeStep(steps: SortedSet<Step>, step: Step) {
        dao.removeStep(step.id)
        steps.remove(step)
    }
}
