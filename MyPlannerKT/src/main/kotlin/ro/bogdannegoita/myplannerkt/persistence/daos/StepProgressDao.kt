package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanProgressEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.StepEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.StepProgressEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.StepProgressRepository
import java.util.*

@Component
class StepProgressDao(
    private val repository: StepProgressRepository,
    private val stepDao: StepDao,
) : StepProgressContainerDao {
    private val dtoMapper = DtoMapper()

    fun create(stepEntity: StepEntity, planProgressEntity: PlanProgressEntity): StepProgressDto {
        var entity = StepProgressEntity(
            completed = false,
            step = stepEntity,
            plan = planProgressEntity,
        )
        entity = repository.save(entity)
        stepEntity.steps.forEach { createSubstep(entity, it) }
        return dtoMapper.stepProgressDto(entity)
    }

    override fun addStepProgress(stepContainerId: UUID, stepId: UUID): StepProgressDto {
        val parentStep = findById(stepContainerId)
        val step = stepDao.findById(stepId)
        return createSubstep(parentStep, step)
    }

    private fun createSubstep(parentStep: StepProgressEntity, step: StepEntity): StepProgressDto {
        var entity = StepProgressEntity(
            completed = false,
            step = step,
            parentStep = parentStep,
        )
        entity = repository.save(entity)
        step.steps.forEach { createSubstep(entity, it) }
        return dtoMapper.stepProgressDto(entity)
    }

    fun findById(id: UUID): StepProgressEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(StepProgressEntity::class) }
    }

    fun findByPlanProgressId(id: UUID): List<StepProgressDto> {
        return repository.findAllByPlanId(id).map(dtoMapper::stepProgressDto)
    }

    fun getSteps(id: UUID): List<StepProgressDto> {
        return repository.findAllByParentStepId(id)
            .map(dtoMapper::stepProgressDto)
    }

    fun update(id: UUID, data: StepProgressDto) {
        val entity = findById(id)
        entity.completed = data.completed
        entity.comment = data.comment
        repository.save(entity)
    }

    override fun updateCompleted(id: UUID, completed: Boolean) {
        val entity = findById(id)
        entity.completed = completed
        repository.save(entity)
    }

    fun countCompletedSteps(stepId: UUID): Int {
        return repository.countByStepIdAndCompletedTrue(stepId)
    }

    override fun removeStepProgress(id: UUID) {
        repository.deleteById(id)
    }
}
