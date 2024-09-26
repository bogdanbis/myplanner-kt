package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "step_progress")
class StepProgressEntity(
    var completed: Boolean? = null,

    @ManyToOne
    var plan: PlanProgressEntity? = null,

    @ManyToOne(fetch = LAZY)
    var step: StepEntity? = null,

    @OneToMany(mappedBy = "parentStep", cascade = [ALL], fetch = LAZY, orphanRemoval = true)
    var steps: MutableList<StepProgressEntity> = mutableListOf(),

    @ManyToOne(fetch = LAZY)
    var parentStep: StepProgressEntity? = null,

    @Id @GeneratedValue
    var id: UUID? = null
)
