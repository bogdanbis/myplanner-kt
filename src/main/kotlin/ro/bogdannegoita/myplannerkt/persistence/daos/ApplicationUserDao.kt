package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.AuthorDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.ApplicationUserRepository
import java.util.*

@Component
class ApplicationUserDao(
    private val repository: ApplicationUserRepository,
) {
    fun findByEmail(email: String): ApplicationUserDto {
        val entity = repository.findByEmail(email)
            ?: throw EntityNotFoundException(ApplicationUserEntity::class)
        return DtoMapper.applicationUserDto(entity)
    }

    fun getAuthor(id: UUID): AuthorDto? {
        return findById(id).author?.let { DtoMapper.authorDto(it) }
    }

    fun findById(id: UUID): ApplicationUserEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(ApplicationUserEntity::class) }
    }
}
