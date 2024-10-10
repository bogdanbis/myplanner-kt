package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressDao

class StepProgress(
    data: StepProgressDto,
    val step: Step,
    parent: StepProgressContainer,
    private val dao: StepProgressDao,
    private val domainFactory: DomainFactory,
) : StepProgressContainer(data.id!!, data.completed, parent, dao, domainFactory), Comparable<StepProgress> {

    private val stepProgressRegistry by domainFactory::stepProgressRegistry

    fun update(data: StepProgressDto): StepProgress {
        data.steps?.forEach { stepProgressData ->
            steps.find { it.id === stepProgressData.id }
                ?.update(stepProgressData)
        }
        if (steps.isEmpty()) {
            completed = data.completed
            dao.updateCompleted(id, completed)
            step.stepCompleted()
        }
        parent?.stepChanged()
        return this
    }

    fun sync() {
        steps = sync(steps, step.steps)
    }

    override fun stepChanged() {
        super.stepChanged()
        step.stepCompleted()
    }

    private var loadedSteps = false
    override fun loadSteps() {
        if (loadedSteps)
            return
        steps = dao.getSteps(id)
            .filter { dto -> step.steps.any { it.id == dto.step!!.id } }
            .map { dto ->
                val step = step.steps.find { it.id == dto.step!!.id }
                val stepProgress = domainFactory.stepProgress(dto, this, step!!)
                stepProgressRegistry[stepProgress.id] = stepProgress
                stepProgress
            }
            .toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: StepProgress) = step.compareTo(other.step)
}
