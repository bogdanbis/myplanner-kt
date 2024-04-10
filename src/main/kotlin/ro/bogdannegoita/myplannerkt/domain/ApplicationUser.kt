package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import java.util.*

open class ApplicationUser(
    data: ApplicationUserDto,
) {
    val id: UUID = data.id!!
    val email: String = data.email
    val firstName: String = data.firstName
    val lastName: String = data.lastName
}
