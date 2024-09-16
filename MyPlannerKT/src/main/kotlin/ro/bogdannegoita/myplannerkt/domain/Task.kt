package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.persistence.daos.TaskDao

class Task(
    private val data: TaskDto,
    private val dao: TaskDao,
) : Comparable<Task> {
    val id = data.id!!
    var title by data::title
    var description by data::description
    var index by data::index

    fun update(data: TaskDto) {
        title = data.title
        description = data.description
        index = data.index
        dao.update(id, this.data)
    }

    val completedTaskCount get(): Int = dao.countCompletedTasks(id)

    override fun compareTo(other: Task) = index.compareTo(other.index)
}
