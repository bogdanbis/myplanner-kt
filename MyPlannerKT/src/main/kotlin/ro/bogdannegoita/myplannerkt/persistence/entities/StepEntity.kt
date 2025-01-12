package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import java.util.*

@Entity
@Table(name = "step")
class StepEntity(
    var title: String? = null,
    var description: String? = null,
    var index: Int? = null,
    var completedStepsCount: Int? = null,

    @ManyToOne
    var plan: PlanEntity? = null,

    @OneToMany(mappedBy = "parentStep", cascade = [ALL])
    var steps: MutableList<StepEntity> = mutableListOf(),

    @ManyToOne
    var parentStep: StepEntity? = null,

    @OneToMany(mappedBy = "step", cascade = [ALL])
    var stepsProgress: MutableList<StepProgressEntity> = mutableListOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
