package ro.bogdannegoita.myplannerkt.domain

import org.springframework.context.ApplicationEventPublisher
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.events.PlanUpdatedEvent
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.time.LocalDateTime
import java.util.*

class Plan(
    val data: PlanDto,
    private val dao: PlanDao,
    private val domainProvider: DomainProvider,
    private val eventPublisher: ApplicationEventPublisher,
) : StepContainer(data.id!!, dao, domainProvider), Comparable<Plan> {
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

    var steps: SortedSet<Step> = sortedSetOf()
        get() {
            loadSteps()
            return field
        }
        private set

    var stats: PlanStats? = null
        get() {
            loadStats()
            return field
        }
        private set

    var acquiredPlans: SortedSet<PlanProgress> = sortedSetOf()
        get() {
            loadAcquiredPlans()
            return field
        }
        private set

    fun update(data: PlanDto) {
        title = data.title
        shortDescription = data.shortDescription
        description = data.description
        color = data.color
        isPublic = data.isPublic
        lastModifiedAt = LocalDateTime.now()
        dao.update(id, this.data)
        if (data.steps != null)
            updateSteps(steps, data.steps)
        eventPublisher.publishEvent(PlanUpdatedEvent(this, this))
    }

    fun acquired() {
        stats?.let { it.numberOfParticipants++ }
    }

    fun getParticipants(): List<PlanProgress> {
        return acquiredPlans.toList()
    }

    fun getParticipantProgress(id: UUID): PlanProgress? {
        return acquiredPlans.find { it.id == id }
    }

    private var loadedAuthor = false
    private fun loadAuthor() {
        if (loadedAuthor)
            return
        author = dao.getAuthor(id)
        loadedAuthor = true
    }

    private var loadedSteps = false
    private fun loadSteps() {
        if (loadedSteps)
            return
        steps = dao.getSteps(id).map(domainProvider::step).toSortedSet()
        loadedSteps = true
    }

    private var loadedStats = false
    private fun loadStats() {
        if (loadedStats)
            return
        stats = domainProvider.planStats(this)
        loadedStats = true
    }

    private var loadedAcquiredPlans = false
    private fun loadAcquiredPlans() {
        if (loadedAcquiredPlans)
            return
        acquiredPlans = dao.getAcquiredPlans(id)
            .map { domainProvider.planProgress(it, this) }
            .toSortedSet()
        loadedAcquiredPlans = true
    }

    override fun compareTo(other: Plan) = other.createdAt.compareTo(createdAt)
}
