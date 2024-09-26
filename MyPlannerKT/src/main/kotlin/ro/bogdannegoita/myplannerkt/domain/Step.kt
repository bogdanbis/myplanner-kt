package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.StepDao
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressDao
import java.util.*

class Step(
    private val data: StepDto,
    private val dao: StepDao,
    private val stepProgressDao: StepProgressDao,
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

    // TODO: remove steps parameter
    fun update(data: StepDto, steps: Collection<StepDto>? = null) {
        title = data.title
        description = data.description
        index = data.index
        if (steps != null)
            updateSteps(steps)
        dao.update(id, this.data)
    }

    private fun updateSteps(steps: Collection<StepDto>) {
        this.steps.forEach { step ->
            if (steps.none { it.id == step.id })
                removeStep(step)
        }

        steps.forEach { step ->
            val existingStep = this.steps.find { it.id == step.id }
            if (existingStep != null)
                existingStep.update(step, step.steps)
            else
                addStep(step)
        }
    }

    private fun addStep(stepData: StepDto) {
        val persistedData = dao.addSubstep(stepData, id)
        val step = domainFactory.step(persistedData)
        steps.add(step)
    }

    private fun removeStep(step: Step) {
        dao.delete(step.id)
        steps.remove(step)
    }

    val completedStepsCount get(): Int = stepProgressDao.countCompletedSteps(id)

    private var loadedSteps = false
    private fun loadSteps() {
        if (loadedSteps)
            return
        steps = dao.getSteps(id).map(domainFactory::step).toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: Step) = index.compareTo(other.index)
}
