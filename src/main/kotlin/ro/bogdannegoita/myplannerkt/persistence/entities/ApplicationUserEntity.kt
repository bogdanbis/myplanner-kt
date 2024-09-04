package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "application_user")
class ApplicationUserEntity(
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,

    @OneToMany(fetch = LAZY)
    var plans: MutableSet<PlanEntity> = mutableSetOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
