package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.persistence.daos.StepDao

class Step(
    private val data: StepDto,
    private val dao: StepDao,
) : Comparable<Step> {
    val id = data.id!!
    var title by data::title
    var description by data::description
    var index by data::index

    fun update(data: StepDto) {
        title = data.title
        description = data.description
        index = data.index
        dao.update(id, this.data)
    }

    val completedStepsCount get(): Int = dao.countCompletedSteps(id)

    override fun compareTo(other: Step) = index.compareTo(other.index)
}
