package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.dtos.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.dtos.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.dtos.StepProgressDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainProvider
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanProgressDao
import java.time.LocalDateTime
import java.util.*

class PlanProgress(
    private val data: PlanProgressDto,
    val plan: Plan,
    private val dao: PlanProgressDao,
    private val domainProvider: DomainProvider,
) : StepProgressContainer(data.id!!, data.completed, null, dao, domainProvider), Comparable<PlanProgress> {

    val acquiredAt by data::acquiredAt
    var lastSyncedPlan by data::lastSyncedPlan
    var lastActive by data::lastActive
    var comment by data::comment
    val user: Lazy<ApplicationUserDto> = lazy { dao.getUser(id) }

    private val stepProgressRegistry: MutableMap<UUID, StepProgress> = mutableMapOf()

    fun update(comment: String?) {
        this.comment = comment
        dao.update(id, data)
    }

    fun updateStepProgress(stepId: UUID, stepProgressData: StepProgressDto): StepProgress? {
        val stepProgress = getStep(stepId)?.update(stepProgressData)
        lastActive = LocalDateTime.now()
        dao.update(id, data)
        return stepProgress
    }

    fun sync() {
        steps = sync(steps, plan.steps)
        lastSyncedPlan = plan.lastModifiedAt
        dao.update(id, data)
    }

    private fun getStep(id: UUID): StepProgress? {
        if (stepProgressRegistry[id] != null)
            return stepProgressRegistry[id]
        for (step in steps) {
            if (step.id == id)
                return step
            val substep = step.steps.find { it.id == id }
            if (substep != null)
                return substep
        }
        return null
    }

    private var loadedSteps = false
    override fun loadSteps() {
        if (loadedSteps) return
        steps = dao.getSteps(id)
            .filter { dto -> plan.steps.any { it.id == dto.step!!.id } }
            .map { dto ->
                val step = plan.steps.find { it.id == dto.step!!.id }
                val stepProgress = domainProvider.stepProgress(dto, this, step!!)
                stepProgressRegistry[stepProgress.id] = stepProgress
                stepProgress
            }
            .toSortedSet()
        loadedSteps = true
    }

    override fun compareTo(other: PlanProgress) = other.acquiredAt.compareTo(acquiredAt)
}
