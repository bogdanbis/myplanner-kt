package ro.bogdannegoita.myplannerkt.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.repositories.ApplicationUserRepository
import ro.bogdannegoita.myplannerkt.security.exceptions.UserAlreadyExistsException
import ro.bogdannegoita.myplannerkt.security.exceptions.UserNotFoundException
import ro.bogdannegoita.myplannerkt.security.web.SignUpRequest

@Service
class UserAuthHelperService(
    val authenticationManager: AuthenticationManager,
    val repository: ApplicationUserRepository,
    val encoder: PasswordEncoder
) {

    fun existsByEmail(email: String): Boolean {
        return repository.existsByEmail(email)
    }

    fun findByEmail(email: String): ApplicationUserEntity {
        return repository.findByEmail(email)
            ?: throw UserNotFoundException()
    }

    fun signup(request: SignUpRequest) {
        val email: String = request.email
        if (existsByEmail(email))
            throw UserAlreadyExistsException(email)
        val user = ApplicationUserEntity(
            request.email, request.firstName,
            request.lastName, encoder.encode(request.password),
        )
        repository.save(user)
    }

    fun login(email: String, password: String?): UserDetailsImpl {
        if (!existsByEmail(email))
            throw UserNotFoundException()
        return authenticationManager
            .authenticate(
                UsernamePasswordAuthenticationToken(
                    email,
                    password
                )
            )
            .principal as UserDetailsImpl
    }
}
