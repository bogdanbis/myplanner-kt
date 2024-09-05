package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.TaskEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.TaskRepository
import java.util.*

@Component
class TaskDao(private val repository: TaskRepository) {
    private val dtoMapper = DtoMapper()

    fun create(data: TaskDto, plan: PlanEntity): TaskDto {
        val entity = TaskEntity(
            title = data.title,
            description = data.description,
            index = data.index,
            plan = plan,
        )
        return dtoMapper.taskDto(repository.save(entity))
    }

    fun update(id: UUID, data: TaskDto) {
        val entity = findById(id)
        entity.title = data.title
        entity.description = data.description
        entity.index = data.index
        repository.save(entity)
    }

    fun findById(id: UUID): TaskEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(TaskEntity::class) }
    }
}
