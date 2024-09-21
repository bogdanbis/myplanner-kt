package ro.bogdannegoita.myplannerkt.domain

import org.springframework.context.ApplicationEventPublisher
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.StepDto
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

    fun update(data: PlanDto, steps: List<StepDto>? = null) {
        title = data.title
        shortDescription = data.shortDescription
        description = data.description
        color = data.color
        isPublic = data.isPublic
        lastModifiedAt = LocalDateTime.now()
        dao.update(id, this.data)
        steps?.forEach { step ->
            val existingStep = this.steps.find { it.id == step.id }
            if (existingStep != null)
                existingStep.update(step)
            else
                addStep(step)
        }
        eventPublisher.publishEvent(PlanUpdatedEvent(this, this))
    }

    private fun addStep(stepData: StepDto): Step {
        val persistedData = dao.addStep(id, stepData)
        val step = domainFactory.step(persistedData)
        steps.add(step)
        return step
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
        steps = dao.getSteps(id).map(domainFactory::step).toSortedSet()
        loadedSteps = true
    }

    private var loadedStats = false
    private fun loadStats() {
        if (loadedStats)
            return
        stats = domainFactory.planStats(this)
        loadedStats = false
    }

    override fun compareTo(other: Plan) = other.createdAt.compareTo(createdAt)
}
