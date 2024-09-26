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
) : StepContainer(data.id!!, dao, domainFactory), Comparable<Step> {
    val id = data.id!!
    var title by data::title
    var description by data::description
    var index by data::index

    var steps: SortedSet<Step> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }

    fun update(data: StepDto) {
        title = data.title
        description = data.description
        index = data.index
        if (data.steps != null)
            updateSteps(steps, data.steps)
        dao.update(id, this.data)
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
