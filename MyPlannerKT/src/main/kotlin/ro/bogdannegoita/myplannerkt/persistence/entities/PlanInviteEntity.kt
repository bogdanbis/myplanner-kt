package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import ro.bogdannegoita.myplannerkt.commons.InviteStatus
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "plan_invite")
class PlanInviteEntity(
    @Enumerated(EnumType.STRING)
    var status: InviteStatus? = null,

    var createdAt: LocalDateTime? = null,
    var respondedAt: LocalDateTime? = null,

    @ManyToOne
    var plan: PlanEntity? = null,

    @ManyToOne
    var sender: ApplicationUserEntity? = null,

    @ManyToOne
    var recipient: ApplicationUserEntity? = null,

    @Id @GeneratedValue
    var id: UUID? = null
)
