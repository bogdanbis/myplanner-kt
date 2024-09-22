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
        val entity = StepEntity(
            title = data.title,
            description = data.description,
            index = data.index,
            plan = plan,
        )
        return dtoMapper.stepDto(repository.save(entity))
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

    fun countCompletedSteps(id: UUID): Int {
        return stepProgressDao.countCompletedSteps(id)
    }
}
