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

    @ManyToOne(fetch = LAZY)
    var plan: PlanEntity? = null,

    @ManyToOne(fetch = LAZY)
    var user: ApplicationUserEntity? = null,

    @OneToMany(mappedBy = "plan", fetch = LAZY, cascade = [ALL])
    var tasks: MutableList<TaskProgressEntity> = mutableListOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
