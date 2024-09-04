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

    fun addPlan(id: UUID, planId: UUID) {
        val userEntity = findById(id)
        val planEntity = planDao.findById(planId)
        userEntity.plans.add(planEntity)
        repository.save(userEntity)
    }

    fun getPlans(id: UUID): List<PlanDto> {
        return findById(id).plans.map(dtoMapper::planDto)
    }
}
