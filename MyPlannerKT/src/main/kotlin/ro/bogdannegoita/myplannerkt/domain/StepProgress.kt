package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.dtos.StepProgressDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressDao

class StepProgress(
    private val data: StepProgressDto,
    val step: Step,
    parent: StepProgressContainer,
    private val dao: StepProgressDao,
    private val domainProvider: DomainProvider,
) : StepProgressContainer(data.id!!, data.completed, parent, dao, domainProvider), Comparable<StepProgress> {

    var comment by data::comment

    fun update(data: StepProgressDto): StepProgress {
        data.steps?.forEach { stepProgressData ->
            steps.find { it.id === stepProgressData.id }
                ?.update(stepProgressData)
        }
        if (steps.isEmpty()) {
            completed = data.completed
            step.stepCompleted()
        }
        comment = data.comment
        dao.update(id, StepProgressDto(id, completed, comment))
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
                domainProvider.stepProgress(dto, this, step!!)
            }
            .toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: StepProgress) = step.compareTo(other.step)
}
