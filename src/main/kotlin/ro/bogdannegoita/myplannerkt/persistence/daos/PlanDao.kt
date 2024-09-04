package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanRepository
import java.util.*

@Component
class PlanDao(
    private val repository: PlanRepository,
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

    fun save(entity: PlanEntity): PlanEntity {
        return repository.save(entity)
    }
}
