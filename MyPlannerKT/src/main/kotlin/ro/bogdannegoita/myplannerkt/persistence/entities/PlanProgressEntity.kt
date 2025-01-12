package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "plan_progress")
class PlanProgressEntity(
    var acquiredAt: LocalDateTime? = null,
    var lastSyncedPlan: LocalDateTime? = null,
    var lastActive: LocalDateTime? = null,
    var completed: Boolean? = null,
    var comment: String? = null,

    @ManyToOne
    var plan: PlanEntity? = null,

    @ManyToOne
    var user: ApplicationUserEntity? = null,

    @OneToMany(mappedBy = "plan", cascade = [ALL])
    var steps: MutableList<StepProgressEntity> = mutableListOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
