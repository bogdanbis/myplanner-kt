package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanProgressEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.StepEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanProgressRepository
import java.time.LocalDateTime
import java.util.*

@Component
class PlanProgressDao(
    private val repository: PlanProgressRepository,
    private val stepProgressDao: StepProgressDao,
    private val stepDao: StepDao,
) : StepProgressContainerDao {
    private val dtoMapper = DtoMapper()

    fun create(planEntity: PlanEntity, userEntity: ApplicationUserEntity): PlanProgressDto {
        var entity = PlanProgressEntity(
            acquiredAt = LocalDateTime.now(),
            lastSyncedPlan = planEntity.lastModifiedAt,
            completed = false,
            plan = planEntity,
            user = userEntity,
        )
        entity = repository.save(entity)
        planEntity.steps.forEach { addStepProgress(it, entity.id!!) }
        return dtoMapper.planProgressDto(entity)
    }

    fun addStepProgress(stepEntity: StepEntity, planProgressId: UUID): StepProgressDto {
        return stepProgressDao.create(stepEntity, findById(planProgressId))
    }

    override fun addStepProgress(stepContainerId: UUID, stepId: UUID): StepProgressDto {
        val planProgressEntity = findById(stepContainerId)
        val stepEntity = stepDao.findById(stepId)
        return stepProgressDao.create(stepEntity, planProgressEntity)
    }

    fun getSteps(id: UUID): List<StepProgressDto> {
        return stepProgressDao.findByPlanProgressId(id)
    }

    fun findById(id: UUID): PlanProgressEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(PlanProgressEntity::class) }
    }

    fun countByPlan(id: UUID): Int {
        return repository.countByPlanId(id)
    }

    override fun removeStepProgress(id: UUID) {
        stepProgressDao.removeStepProgress(id)
    }

    fun update(id: UUID, data: PlanProgressDto) {
        val entity = findById(id)
        entity.lastSyncedPlan = data.lastSyncedPlan
        entity.completed = data.completed
        repository.save(entity)
    }

    override fun updateCompleted(id: UUID, completed: Boolean) {
        val entity = findById(id)
        entity.completed = completed
        repository.save(entity)
    }
}
