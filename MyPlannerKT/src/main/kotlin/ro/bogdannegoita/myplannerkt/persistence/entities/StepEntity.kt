package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "step")
class StepEntity(
    var title: String? = null,
    var description: String? = null,
    var index: Int? = null,
    var completedStepsCount: Int? = null,

    @ManyToOne(fetch = LAZY)
    var plan: PlanEntity? = null,

    @OneToMany(mappedBy = "parentStep", cascade = [ALL], fetch = LAZY)
    var steps: MutableList<StepEntity> = mutableListOf(),

    @ManyToOne(fetch = LAZY)
    var parentStep: StepEntity? = null,

    @OneToMany(mappedBy = "step", cascade = [ALL], fetch = LAZY)
    var stepsProgress: MutableList<StepProgressEntity> = mutableListOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
