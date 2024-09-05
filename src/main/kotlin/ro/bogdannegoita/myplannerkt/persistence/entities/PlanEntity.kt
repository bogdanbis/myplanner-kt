package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "plan")
class PlanEntity(
    var title: String? = null,
    var description: String? = null,
    var color: String? = null,
    var isPublic: Boolean? = null,
    var createdAt: LocalDateTime? = null,

    @ManyToOne(fetch = LAZY)
    var author: ApplicationUserEntity? = null,

    @OneToMany(mappedBy = "plan", fetch = LAZY)
    var tasks: MutableList<TaskEntity> = mutableListOf(),

    @OneToMany(mappedBy = "plan", fetch = LAZY)
    var acquiredPlans: MutableList<PlanProgressEntity> = mutableListOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
