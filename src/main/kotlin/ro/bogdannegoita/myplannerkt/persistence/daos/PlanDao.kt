package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.ApplicationUserRepository
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanRepository
import java.util.*

@Component
class PlanDao(
    private val repository: PlanRepository,
    private val applicationUserRepository: ApplicationUserRepository
) {
    private val dtoMapper = DtoMapper()

    fun getPublicPlans(): List<PlanDto> {
        return repository.findByIsPublicTrue().map(dtoMapper::planDto)
    }

    fun getAuthorsOf(planId: UUID): List<ApplicationUserDto> {
        return findById(planId).authors.map(dtoMapper::applicationUserDto).toList()
    }

    fun getById(id: UUID): PlanDto {
        return dtoMapper.planDto(findById(id))
    }

    fun findById(id: UUID): PlanEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(PlanEntity::class) }
    }

    fun createPlan(data: PlanDto, authorsIds: Collection<UUID>): PlanDto {
        val authorEntities: MutableSet<ApplicationUserEntity> = applicationUserRepository.findAllById(authorsIds)
            .toMutableSet()
        val planEntity = PlanEntity(
            title = data.title,
            description = data.description,
            color = data.color,
            isPublic = data.isPublic,
            authors = authorEntities,
        )
        return dtoMapper.planDto(repository.save(planEntity))
    }
}
