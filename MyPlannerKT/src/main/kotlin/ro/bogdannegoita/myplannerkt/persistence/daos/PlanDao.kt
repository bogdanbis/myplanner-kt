package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanRepository
import java.util.*

@Component
class PlanDao(
    private val repository: PlanRepository,
    private val stepDao: StepDao,
    private val planProgressDao: PlanProgressDao,
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
            shortDescription = data.shortDescription,
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
        entity.shortDescription = data.shortDescription
        entity.description = data.description
        entity.color = data.color
        entity.isPublic = data.isPublic
        entity.lastModifiedAt = data.lastModifiedAt
        repository.save(entity)
    }

    fun getSteps(id: UUID): Collection<StepDto> {
        return findById(id).steps.map(dtoMapper::stepDto)
    }

    fun addStep(id: UUID, stepData: StepDto): StepDto {
        val planEntity = findById(id)
        return stepDao.create(stepData, planEntity)
    }

    fun delete(id: UUID) {
        repository.deleteById(id)
    }

    fun getNumberOfAcquiredPlans(id: UUID): Int {
        return planProgressDao.countByPlan(id)
    }
}
