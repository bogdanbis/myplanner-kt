package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class AuthorDto(
    var id: UUID? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
)
