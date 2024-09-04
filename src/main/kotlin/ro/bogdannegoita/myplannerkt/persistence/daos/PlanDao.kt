package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanRepository
import java.util.*

@Component
class PlanDao(
    private val repository: PlanRepository,
    private val taskDao: TaskDao,
) {
    private val dtoMapper = DtoMapper()

    fun getPublicPlans(): List<PlanDto> {
        return repository.findByIsPublicTrue().map(dtoMapper::planDto)
    }

    fun getAuthor(planId: UUID): ApplicationUserDto {
        return findById(planId).author?.let { dtoMapper.applicationUserDto(it) }!!
    }

    fun getById(id: UUID): PlanDto {
        return dtoMapper.planDto(findById(id))
    }

    fun findById(id: UUID): PlanEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(PlanEntity::class) }
    }

    fun create(data: PlanDto, author: ApplicationUserEntity): PlanDto {
        val entity = PlanEntity(
            title = data.title,
            description = data.description,
            color = data.color,
            isPublic = data.isPublic,
            createdAt = data.createdAt,
            author = author,
        )
        return dtoMapper.planDto(repository.save(entity))
    }

    fun update(id: UUID, data: PlanDto) {
        val entity = findById(id)
        entity.title = data.title
        entity.description = data.description
        entity.color = data.color
        entity.isPublic = data.isPublic
        repository.save(entity)
    }

    fun getTasks(id: UUID): Collection<TaskDto> {
        return findById(id).tasks.map(dtoMapper::taskDto)
    }

    fun addTask(id: UUID, taskData: TaskDto): TaskDto {
        val planEntity = findById(id)
        return taskDao.create(taskData, planEntity)
    }
}
