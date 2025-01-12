package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import java.util.*

@Entity
@Table(name = "step_progress")
class StepProgressEntity(
    var completed: Boolean? = null,
    var comment: String? = null,

    @ManyToOne
    var plan: PlanProgressEntity? = null,

    @ManyToOne
    var step: StepEntity? = null,

    @OneToMany(mappedBy = "parentStep", cascade = [ALL], orphanRemoval = true)
    var steps: MutableList<StepProgressEntity> = mutableListOf(),

    @ManyToOne
    var parentStep: StepProgressEntity? = null,

    @Id @GeneratedValue
    var id: UUID? = null
)
