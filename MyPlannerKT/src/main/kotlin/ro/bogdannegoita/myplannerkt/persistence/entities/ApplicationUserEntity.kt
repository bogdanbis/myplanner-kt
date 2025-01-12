package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.EAGER
import java.util.*

@Entity
@Table(name = "application_user")
class ApplicationUserEntity(
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,

    @OneToOne(mappedBy = "user", cascade = [(ALL)], fetch = EAGER, orphanRemoval = true)
    var uiPreferences: UserUIPreferencesEntity? = null,

    @OneToMany(mappedBy = "author")
    var createdPlans: MutableSet<PlanEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "user", cascade = [ALL])
    var acquiredPlans: MutableSet<PlanProgressEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "sender", cascade = [ALL])
    var sentInvites: MutableList<PlanInviteEntity> = mutableListOf(),

    @OneToMany(mappedBy = "recipient", cascade = [ALL])
    var receivedInvites: MutableList<PlanInviteEntity> = mutableListOf(),

    @Id @GeneratedValue
    var id: UUID? = null
)
