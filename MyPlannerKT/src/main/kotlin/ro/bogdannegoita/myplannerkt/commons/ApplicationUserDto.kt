package ro.bogdannegoita.myplannerkt.commons

import ro.bogdannegoita.myplannerkt.domain.types.UserUIPreferences
import java.util.*

data class ApplicationUserDto(
    var id: UUID?,
    var email: String,
    var firstName: String,
    var lastName: String,
    var uiPreferences: UserUIPreferences,
)
