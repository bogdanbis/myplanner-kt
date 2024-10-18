package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.persistence.daos.StepDao
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressDao
import java.util.*

class Step(
    private val data: StepDto,
    private val dao: StepDao,
    private val stepProgressDao: StepProgressDao,
    private val domainProvider: DomainProvider,
) : StepContainer(data.id!!, dao, domainProvider), Comparable<Step> {
    val id = data.id!!
    var title by data::title
    var description by data::description
    var index by data::index
    var completedStepsCount by data::completedStepsCount
        private set

    var steps: SortedSet<Step> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }
        private set

    fun update(data: StepDto) {
        title = data.title
        description = data.description
        index = data.index
        if (data.steps != null) {
            updateSteps(steps, data.steps)
            completedStepsCount = stepProgressDao.countCompletedSteps(id)
        }
        dao.update(id, this.data)
    }

    fun stepCompleted() {
        completedStepsCount = stepProgressDao.countCompletedSteps(id)
        dao.update(id, data)
    }

    private var loadedSteps = false
    private fun loadSteps() {
        if (loadedSteps)
            return
        steps = dao.getSteps(id).map(domainProvider::step).toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: Step) = index.compareTo(other.index)
}
