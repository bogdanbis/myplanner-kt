package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.AuthorDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.AuthorEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.AuthorRepository
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanRepository
import java.util.*

@Component
class PlanDao(private val repository: PlanRepository, private val authorRepository: AuthorRepository) {
    fun getPublicPlans(): List<PlanDto> {
        return repository.findByIsPublicTrue().map(DtoMapper::planDto)
    }

    fun getAuthorsOf(planId: UUID): List<AuthorDto> {
        return findById(planId).authors.map(DtoMapper::authorDto).toList()
    }

    fun getById(id: UUID): PlanDto {
        return DtoMapper.planDto(findById(id))
    }

    fun findById(id: UUID): PlanEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(PlanEntity::class) }
    }

    fun createPlan(data: PlanDto, authorsIds: Collection<UUID>): PlanDto {
        val authorEntities: MutableSet<AuthorEntity> = authorRepository.findAllById(authorsIds).toMutableSet()
        val planEntity = PlanEntity(
            title = data.title,
            description = data.description,
            color = data.color,
            isPublic = data.isPublic,
            authors = authorEntities,
        )
        return DtoMapper.planDto(repository.save(planEntity))
    }
}
