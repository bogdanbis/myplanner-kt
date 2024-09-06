package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "task_progress")
class TaskProgressEntity(
    var completed: Boolean? = null,

    @ManyToOne
    var plan: PlanProgressEntity? = null,

    @ManyToOne(fetch = LAZY)
    var task: TaskEntity? = null,

    @Id @GeneratedValue
    var id: UUID? = null
)
