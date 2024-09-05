package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanProgressDao

class PlanProgress(
    private val data: PlanProgressDto,
    val plan: Plan,
    private val dao: PlanProgressDao,
    private val domainFactory: DomainFactory,
) : Comparable<PlanProgress> {
    val id = data.id!!
    val acquiredAt by data::acquiredAt
    var tasks: MutableList<TaskProgress> = mutableListOf()
        get() {
            loadTasks()
            return field
        }
        private set

    private var loadedTasks = false
    private fun loadTasks() {
        if (loadedTasks) return
        tasks = dao.getTasks(id)
            .map { taskDto ->
                val task = plan.tasks.find { it.id == taskDto.task.id }
                val taskProgress = domainFactory.taskProgress(taskDto, task!!)
                taskProgress
            }
            .toMutableList()
        loadedTasks = true
    }

    override fun compareTo(other: PlanProgress) = other.acquiredAt.compareTo(acquiredAt)
}
