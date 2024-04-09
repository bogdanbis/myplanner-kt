package ro.bogdannegoita.myplannerkt.persistence.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import java.util.*

@Repository
interface ApplicationUserRepository : JpaRepository<ApplicationUserEntity, UUID> {
    fun findByEmail(email: String): ApplicationUserEntity?
    fun existsByEmail(email: String): Boolean
}
