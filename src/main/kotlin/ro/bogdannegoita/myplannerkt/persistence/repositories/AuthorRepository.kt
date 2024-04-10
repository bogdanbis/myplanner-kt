package ro.bogdannegoita.myplannerkt.persistence.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ro.bogdannegoita.myplannerkt.persistence.entities.AuthorEntity
import java.util.*

@Repository
interface AuthorRepository : JpaRepository<AuthorEntity, UUID>
