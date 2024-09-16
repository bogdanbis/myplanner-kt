package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.TaskProgressDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanProgressEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.TaskEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.TaskProgressEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.TaskProgressRepository
import java.util.*

@Component
class TaskProgressDao(private val repository: TaskProgressRepository) {
    private val dtoMapper = DtoMapper()

    fun create(taskEntity: TaskEntity, planProgressEntity: PlanProgressEntity): TaskProgressDto {
        val entity = TaskProgressEntity(
            completed = false,
            task = taskEntity,
            plan = planProgressEntity,
        )
        return dtoMapper.taskProgressDto(repository.save(entity))
    }

    fun findById(id: UUID): TaskProgressEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(TaskProgressEntity::class) }
    }

    fun findByPlanProgressId(id: UUID): List<TaskProgressDto> {
        return repository.findAllByPlanId(id).map(dtoMapper::taskProgressDto)
    }

    fun update(id: UUID, data: TaskProgressDto) {
        val entity = findById(id)
        entity.completed = data.completed
        repository.save(entity)
    }
}
