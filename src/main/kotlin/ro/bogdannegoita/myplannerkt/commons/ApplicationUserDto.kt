package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class ApplicationUserDto(
    var id: UUID?,
    var email: String,
    var firstName: String,
    var lastName: String,
)
