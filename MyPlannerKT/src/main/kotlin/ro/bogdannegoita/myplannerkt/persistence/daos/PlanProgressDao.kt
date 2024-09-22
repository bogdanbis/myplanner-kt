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
) {
    private val dtoMapper = DtoMapper()

    fun create(planEntity: PlanEntity, userEntity: ApplicationUserEntity): PlanProgressDto {
        var entity = PlanProgressEntity(
            acquiredAt = LocalDateTime.now(),
            lastSyncedPlan = planEntity.lastModifiedAt,
            plan = planEntity,
            user = userEntity,
        )
        entity = repository.save(entity)
        planEntity.steps.forEach { createStepProgress(it, entity.id!!) }
        return dtoMapper.planProgressDto(entity)
    }

    fun createStepProgress(stepEntity: StepEntity, planProgressId: UUID): StepProgressDto {
        return stepProgressDao.create(stepEntity, findById(planProgressId))
    }

    fun createStepProgress(stepId: UUID, planProgressId: UUID): StepProgressDto {
        val planProgressEntity = findById(planProgressId)
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

    fun deleteStep(stepProgressId: UUID) {
        stepProgressDao.delete(stepProgressId)
    }

    fun update(id: UUID, data: PlanProgressDto): PlanProgressDto {
        val entity = findById(id)
        entity.lastSyncedPlan = data.lastSyncedPlan
        return dtoMapper.planProgressDto(repository.save(entity))
    }
}
