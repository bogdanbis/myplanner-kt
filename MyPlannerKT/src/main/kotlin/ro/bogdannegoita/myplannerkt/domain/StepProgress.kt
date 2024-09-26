package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressDao
import java.util.*

class StepProgress(
    private val data: StepProgressDto,
    val step: Step,
    private val dao: StepProgressDao,
    private val domainFactory: DomainFactory,
) : StepProgressContainer(data.id!!, dao, domainFactory), Comparable<StepProgress> {
    val id: UUID = data.id!!
    var completed by data::completed

    var steps: SortedSet<StepProgress> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }
        private set

    private val stepProgressRegistry by domainFactory::stepProgressRegistry

    fun update(data: StepProgressDto): StepProgress {
        completed = data.completed
        dao.update(id, this.data)
        data.steps?.forEach { stepProgressData ->
            steps.find { it.id === stepProgressData.id }
                ?.update(stepProgressData)
        }
        return this
    }

    fun sync() {
        steps = sync(steps, step.steps)
    }

    private var loadedSteps = false
    private fun loadSteps() {
        if (loadedSteps)
            return
        steps = dao.getSteps(id)
            .filter { dto -> step.steps.any { it.id == dto.step!!.id } }
            .map { dto ->
                val step = step.steps.find { it.id == dto.step!!.id }
                val stepProgress = domainFactory.stepProgress(dto, step!!)
                stepProgressRegistry[stepProgress.id] = stepProgress
                stepProgress
            }
            .toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: StepProgress) = step.compareTo(other.step)
}
