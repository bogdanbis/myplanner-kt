package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "author")
class AuthorEntity(
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,

    @OneToOne(fetch = LAZY)
    var user: ApplicationUserEntity? = null,

    @ManyToMany(mappedBy = "authors", fetch = LAZY, cascade = [ALL])
    var plans: MutableSet<PlanEntity> = mutableSetOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
