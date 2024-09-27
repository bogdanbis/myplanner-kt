package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "plan_progress")
class PlanProgressEntity(
    var acquiredAt: LocalDateTime? = null,
    var lastSyncedPlan: LocalDateTime? = null,
    var completed: Boolean? = null,

    @ManyToOne(fetch = LAZY)
    var plan: PlanEntity? = null,

    @ManyToOne(fetch = LAZY)
    var user: ApplicationUserEntity? = null,

    @OneToMany(mappedBy = "plan", fetch = LAZY, cascade = [ALL])
    var steps: MutableList<StepProgressEntity> = mutableListOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
