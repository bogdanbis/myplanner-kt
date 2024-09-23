package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.StepEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.StepRepository
import java.util.*

@Component
class StepDao(
    private val repository: StepRepository,
    private val stepProgressDao: StepProgressDao,
) {
    private val dtoMapper = DtoMapper()

    fun create(data: StepDto, plan: PlanEntity): StepDto {
        var entity = StepEntity(
            title = data.title,
            description = data.description,
            index = data.index,
            plan = plan,
        )
        entity = repository.save(entity)
        data.steps?.forEach { createSubstep(it, entity) }
        return dtoMapper.stepDto(entity)
    }

    fun createSubstep(data: StepDto, parentStepId: UUID): StepDto {
        val parentStep = findById(parentStepId)
        return createSubstep(data, parentStep)
    }

    private fun createSubstep(data: StepDto, parentStep: StepEntity): StepDto {
        var entity = StepEntity(
            title = data.title,
            description = data.description,
            index = data.index,
            parentStep = parentStep,
        )
        entity = repository.save(entity)
        data.steps?.forEach { createSubstep(it, entity) }
        return dtoMapper.stepDto(entity)
    }

    fun update(id: UUID, data: StepDto) {
        val entity = findById(id)
        entity.title = data.title
        entity.description = data.description
        entity.index = data.index
        repository.save(entity)
    }

    fun findById(id: UUID): StepEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(StepEntity::class) }
    }

    fun findByStepId(id: UUID): List<StepDto> {
        return repository.findAllByParentStepId(id)
            .map { dtoMapper.stepDto(it) }
    }

    fun countCompletedSteps(id: UUID): Int {
        return stepProgressDao.countCompletedSteps(id)
    }

    fun delete(id: UUID) {
        repository.deleteById(id)
    }
}
