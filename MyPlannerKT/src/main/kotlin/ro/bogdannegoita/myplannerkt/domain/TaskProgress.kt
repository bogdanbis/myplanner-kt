package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.TaskProgressDto
import ro.bogdannegoita.myplannerkt.persistence.daos.TaskProgressDao
import java.util.*

class TaskProgress(
    private val data: TaskProgressDto,
    val task: Task,
    private val dao: TaskProgressDao
): Comparable<TaskProgress> {
    val id: UUID = data.id!!
    var completed by data::completed

    fun update(data: TaskProgressDto): TaskProgress {
        completed = data.completed
        dao.update(id, this.data)
        return this
    }

    override fun compareTo(other: TaskProgress) = task.compareTo(other.task)
}
