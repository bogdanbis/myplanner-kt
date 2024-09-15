package ro.bogdannegoita.myplannerkt.domain

import org.springframework.context.ApplicationEventPublisher
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.events.PlanUpdatedEvent
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.time.LocalDateTime
import java.util.*

class Plan(
    val data: PlanDto,
    private val dao: PlanDao,
    private val domainFactory: DomainFactory,
    private val eventPublisher: ApplicationEventPublisher,
) : Comparable<Plan> {
    val id = data.id!!
    var title by data::title
    var shortDescription by data::shortDescription
    var description by data::description
    var color by data::color
    var isPublic by data::isPublic
    val createdAt by data::createdAt
    var lastModifiedAt by data::lastModifiedAt
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
    var stats: PlanStats? = null
        get() {
            loadStats()
            return field
        }
        private set

    fun update(data: PlanDto, tasks: List<TaskDto>? = null) {
        title = data.title
        shortDescription = data.shortDescription
        description = data.description
        color = data.color
        isPublic = data.isPublic
        lastModifiedAt = LocalDateTime.now()
        eventPublisher.publishEvent(PlanUpdatedEvent(this, this))
        dao.update(id, this.data)
        tasks?.forEach { task ->
            val existingTask = this.tasks.find { it.id == task.id }
            if (existingTask != null)
                existingTask.update(task)
            else
                addTask(task)
        }
    }

    private fun addTask(taskData: TaskDto): Task {
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

    private var loadedStats = false
    fun loadStats() {
        if (loadedStats)
            return
        stats = domainFactory.planStats(this)
        loadedStats = false
    }

    override fun compareTo(other: Plan) = other.createdAt.compareTo(createdAt)
}
