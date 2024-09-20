package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import ro.bogdannegoita.myplannerkt.persistence.daos.StepProgressDao
import java.util.*

class StepProgress(
    private val data: StepProgressDto,
    val step: Step,
    private val dao: StepProgressDao
): Comparable<StepProgress> {
    val id: UUID = data.id!!
    var completed by data::completed

    fun update(data: StepProgressDto): StepProgress {
        completed = data.completed
        dao.update(id, this.data)
        return this
    }

    override fun compareTo(other: StepProgress) = step.compareTo(other.step)
}
