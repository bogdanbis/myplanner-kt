package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "application_user")
class ApplicationUserEntity(
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,
    @Id @GeneratedValue
    var id: UUID? = null
)
