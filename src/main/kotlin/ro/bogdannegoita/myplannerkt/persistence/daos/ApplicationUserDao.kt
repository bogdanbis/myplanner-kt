package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.ApplicationUserRepository
import java.util.*

@Component
class ApplicationUserDao(
    private val repository: ApplicationUserRepository,
    private val planDao: PlanDao,
) {
    private val dtoMapper = DtoMapper()

    fun findByEmail(email: String): ApplicationUserDto {
        val entity = repository.findByEmail(email)
            ?: throw EntityNotFoundException(ApplicationUserEntity::class)
        return dtoMapper.applicationUserDto(entity)
    }

    fun findById(id: UUID): ApplicationUserEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(ApplicationUserEntity::class) }
    }

    fun acquirePlan(userId: UUID, planId: UUID) {
        val userEntity = findById(userId)
        userEntity.acquiredPlans.add(planDao.findById(planId))
        repository.save(userEntity)
    }

    fun createPlan(data: PlanDto, authorId: UUID): PlanDto {
        return planDao.create(data, findById(authorId))
    }

    fun getAcquiredPlans(id: UUID): List<PlanDto> {
        return findById(id).acquiredPlans.map(dtoMapper::planDto)
    }

    fun getCreatedPlans(id: UUID): List<PlanDto> {
        return findById(id).createdPlans.map(dtoMapper::planDto)
    }
}
