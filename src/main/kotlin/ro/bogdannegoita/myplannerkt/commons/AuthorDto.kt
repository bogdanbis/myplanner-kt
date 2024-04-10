package ro.bogdannegoita.myplannerkt.commons

import java.util.*

data class AuthorDto(
    var id: UUID?,
    var firstName: String,
    var lastName: String,
    var email: String,
)
