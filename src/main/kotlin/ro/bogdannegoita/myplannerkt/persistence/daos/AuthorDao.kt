package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.AuthorDto
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.AuthorEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.AuthorRepository

@Component
class AuthorDao(private val repository: AuthorRepository) {

    fun create(data: AuthorDto, user: ApplicationUserEntity): AuthorDto {
        val entity = AuthorEntity(
            firstName = data.firstName, lastName = data.lastName,
            email = data.email, user = user)
        return DtoMapper.authorDto(repository.save(entity))
    }

}
