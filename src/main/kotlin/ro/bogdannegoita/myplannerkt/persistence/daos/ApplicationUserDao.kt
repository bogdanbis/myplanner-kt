package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.ApplicationUserRepository

@Component
class ApplicationUserDao(
    private val repository: ApplicationUserRepository,
) {
    fun findByEmail(email: String): ApplicationUserDto {
        val entity = repository.findByEmail(email)
            ?: throw EntityNotFoundException(ApplicationUserEntity::class)
        return DtoMapper.applicationUserDto(entity)
    }
}
