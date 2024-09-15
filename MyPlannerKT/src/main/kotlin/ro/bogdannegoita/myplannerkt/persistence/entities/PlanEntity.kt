package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "plan")
class PlanEntity(
    var title: String? = null,
    var shortDescription: String? = null,
    @Column(length = 1000)
    var description: String? = null,
    var color: String? = null,
    var isPublic: Boolean? = null,
    var createdAt: LocalDateTime? = null,
    var lastModifiedAt: LocalDateTime? = null,

    @ManyToOne(fetch = LAZY)
    var author: ApplicationUserEntity? = null,

    @OneToMany(mappedBy = "plan", fetch = LAZY, cascade = [ALL])
    var tasks: MutableList<TaskEntity> = mutableListOf(),

    @OneToMany(mappedBy = "plan", fetch = LAZY)
    var acquiredPlans: MutableList<PlanProgressEntity> = mutableListOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
