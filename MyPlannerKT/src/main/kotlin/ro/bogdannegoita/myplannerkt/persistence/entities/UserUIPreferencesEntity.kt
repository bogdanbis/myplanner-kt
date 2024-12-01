package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "application_user_ui_preferences")
class UserUIPreferencesEntity(
    @Column(columnDefinition = "text")
    var pinnedPlans: String? = null,

    @OneToOne
    var user: ApplicationUserEntity? = null,

    @Id @GeneratedValue
    var id: UUID? = null
)
