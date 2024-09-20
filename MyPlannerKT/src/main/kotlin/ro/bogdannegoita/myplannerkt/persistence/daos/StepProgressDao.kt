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
class StepProgressDao(private val repository: StepProgressRepository) {
    private val dtoMapper = DtoMapper()

    fun create(stepEntity: StepEntity, planProgressEntity: PlanProgressEntity): StepProgressDto {
        val entity = StepProgressEntity(
            completed = false,
            step = stepEntity,
            plan = planProgressEntity,
        )
        return dtoMapper.stepProgressDto(repository.save(entity))
    }

    fun findById(id: UUID): StepProgressEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(StepProgressEntity::class) }
    }

    fun findByPlanProgressId(id: UUID): List<StepProgressDto> {
        return repository.findAllByPlanId(id).map(dtoMapper::stepProgressDto)
    }

    fun update(id: UUID, data: StepProgressDto) {
        val entity = findById(id)
        entity.completed = data.completed
        repository.save(entity)
    }

    fun countCompletedSteps(stepId: UUID): Int {
        return repository.countByStepIdAndCompletedTrue(stepId)
    }
}
