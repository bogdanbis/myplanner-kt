package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.TaskEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.TaskRepository

@Component
class TaskDao(private val repository: TaskRepository) {
    private val dtoMapper = DtoMapper()

    fun create(data: TaskDto, plan: PlanEntity): TaskDto {
        val entity = TaskEntity(
            title = data.title,
            description = data.description,
            index = data.index,
            plan = plan)
        return dtoMapper.taskDto(repository.save(entity))
    }
}
