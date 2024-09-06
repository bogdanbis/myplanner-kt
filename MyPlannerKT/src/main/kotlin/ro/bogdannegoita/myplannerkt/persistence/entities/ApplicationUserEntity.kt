package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "application_user")
class ApplicationUserEntity(
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,

    @OneToMany(mappedBy = "author", fetch = LAZY)
    var createdPlans: MutableSet<PlanEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = [ALL])
    var acquiredPlans: MutableSet<PlanProgressEntity> = mutableSetOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
