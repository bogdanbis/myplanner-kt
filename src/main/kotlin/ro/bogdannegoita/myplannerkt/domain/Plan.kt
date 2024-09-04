package ro.bogdannegoita.myplannerkt.domain

import org.springframework.context.ApplicationEventPublisher
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.events.PlanUpdatedEvent
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.util.*

class Plan(
    private val data: PlanDto,
    private val dao: PlanDao,
    private val domainFactory: DomainFactory,
    private val eventPublisher: ApplicationEventPublisher,
) : Comparable<Plan> {
    val id = data.id!!
    var title by data::title
    var description by data::description
    var color by data::color
    var isPublic by data::isPublic
    val createdAt by data::createdAt
    var author: ApplicationUserDto? = null
        get() {
            loadAuthor()
            return field
        }
        private set
    var tasks: SortedSet<Task> = sortedSetOf()
        get() {
            loadTasks()
            return field
        }
        private set

    fun update(data: PlanDto) {
        title = data.title
        description = data.description
        color = data.color
        isPublic = data.isPublic
        eventPublisher.publishEvent(PlanUpdatedEvent(this, this))
        dao.update(id, this.data)
    }

    fun addTasks(tasks: Collection<TaskDto>) {
        tasks.forEach { addTask(it) }
    }

    fun addTask(taskData: TaskDto): Task {
        val persistedData = dao.addTask(id, taskData)
        val task = domainFactory.task(persistedData)
        tasks.add(task)
        return task
    }

    private var loadedAuthor = false
    private fun loadAuthor() {
        if (loadedAuthor)
            return
        author = dao.getAuthor(id)
        loadedAuthor = true
    }

    private var loadedTasks = false
    private fun loadTasks() {
        if (loadedTasks)
            return
        tasks = dao.getTasks(id).map(domainFactory::task).toSortedSet()
        loadedTasks = true
    }

    override fun compareTo(other: Plan) = createdAt.compareTo(other.createdAt)
}
