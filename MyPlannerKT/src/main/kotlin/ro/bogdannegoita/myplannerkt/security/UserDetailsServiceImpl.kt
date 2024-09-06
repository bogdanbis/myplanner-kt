package ro.bogdannegoita.myplannerkt.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ro.bogdannegoita.myplannerkt.persistence.repositories.ApplicationUserRepository
import ro.bogdannegoita.myplannerkt.security.exceptions.UserNotFoundException

@Service
class UserDetailsServiceImpl(val repository: ApplicationUserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity = repository.findByEmail(email)
            ?: throw UserNotFoundException()
        return UserDetailsImpl(userEntity.email!!, userEntity.password!!)
    }
}
