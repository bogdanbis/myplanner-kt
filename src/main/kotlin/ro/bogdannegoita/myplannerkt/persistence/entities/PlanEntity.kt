package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "plan")
class PlanEntity(
    var title: String? = null,
    var description: String? = null,
    var color: String? = null,
    var isPublic: Boolean? = null,

    @ManyToOne(fetch = LAZY)
    var author: ApplicationUserEntity? = null,

    @ManyToMany(mappedBy = "acquiredPlans", fetch = LAZY)
    var users: MutableSet<ApplicationUserEntity> = mutableSetOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
