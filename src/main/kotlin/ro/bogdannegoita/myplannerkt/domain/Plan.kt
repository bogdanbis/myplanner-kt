package ro.bogdannegoita.myplannerkt.domain

import org.springframework.context.ApplicationEventPublisher
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.events.PlanUpdatedEvent
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

class Plan(
    private val data: PlanDto,
    private val dao: PlanDao,
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

    fun update(data: PlanDto) {
        title = data.title
        description = data.description
        color = data.color
        isPublic = data.isPublic
        eventPublisher.publishEvent(PlanUpdatedEvent(this, this))
        dao.update(id, this.data)
    }

    private var loadedAuthor = false
    private fun loadAuthor() {
        if (loadedAuthor)
            return
        author = dao.getAuthor(id)
        loadedAuthor = true
    }

    override fun compareTo(other: Plan) = createdAt.compareTo(other.createdAt)
}
