package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.StepDao
import java.util.*

class Step(
    private val data: StepDto,
    private val dao: StepDao,
    private val domainFactory: DomainFactory,
) : Comparable<Step> {
    val id = data.id!!
    var title by data::title
    var description by data::description
    var index by data::index

    var steps: SortedSet<Step> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }

    fun update(data: StepDto, steps: Collection<StepDto>? = null) {
        title = data.title
        description = data.description
        index = data.index
        if (steps != null)
            updateSteps(steps)
        dao.update(id, this.data)
    }

    private fun updateSteps(steps: Collection<StepDto>) {
        this.steps.filter { step -> steps.none { it.id == step.id } }
            .forEach { removeStep(it) }

        steps.forEach { step ->
            val existingStep = this.steps.find { it.id == step.id }
            if (existingStep != null)
                existingStep.update(step, step.steps)
            else
                addStep(step)
        }
    }

    private fun addStep(stepData: StepDto): Step {
        val persistedData = dao.createSubstep(stepData, id)
        val step = domainFactory.step(persistedData)
        steps.add(step)
        return step
    }

    private fun removeStep(step: Step) {
        dao.delete(step.id)
        steps.remove(step)
    }

    val completedStepsCount get(): Int = dao.countCompletedSteps(id)

    private var loadedSteps = false
    private fun loadSteps() {
        if (loadedSteps)
            return
        steps = dao.findByStepId(id)
            .map { domainFactory.step(it) }
            .toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: Step) = index.compareTo(other.index)
}
