package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.TaskDto

class Task(
    private val data: TaskDto,
) : Comparable<Task> {
    val id = data.id!!
    val title by data::title
    val description by data::description
    val index by data::index

    override fun compareTo(other: Task) = index.compareTo(other.index)
}
