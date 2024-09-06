package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "task")
class TaskEntity(
    var title: String? = null,
    var description: String? = null,
    var index: Int? = null,

    @ManyToOne(fetch = LAZY)
    var plan: PlanEntity? = null,

    @Id @GeneratedValue
    var id: UUID? = null
)
