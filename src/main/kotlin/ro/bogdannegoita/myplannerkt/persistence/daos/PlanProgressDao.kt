package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.TaskProgressDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanProgressEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.TaskEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanProgressRepository
import java.time.LocalDateTime
import java.util.*

@Component
class PlanProgressDao(
    private val repository: PlanProgressRepository,
    private val taskProgressDao: TaskProgressDao,
) {
    private val dtoMapper = DtoMapper()

    fun create(planEntity: PlanEntity, userEntity: ApplicationUserEntity): PlanProgressDto {
        val entity = PlanProgressEntity(
            acquiredAt = LocalDateTime.now(),
            plan = planEntity,
            user = userEntity,
        )
        return dtoMapper.planProgressDto(repository.save(entity))
    }

    fun createTaskProgress(taskEntity: TaskEntity, planProgressId: UUID): TaskProgressDto {
        return taskProgressDao.create(taskEntity, findById(planProgressId))
    }

    fun getTasks(id: UUID): List<TaskProgressDto> {
        return findById(id).tasks.map(dtoMapper::taskProgressDto)
    }

    fun findById(id: UUID): PlanProgressEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(PlanProgressEntity::class) }
    }
}
