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

    @ManyToMany(fetch = LAZY)
    var authors: MutableSet<AuthorEntity> = mutableSetOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
