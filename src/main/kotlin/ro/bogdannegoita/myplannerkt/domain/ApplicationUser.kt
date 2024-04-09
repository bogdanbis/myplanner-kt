package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import java.util.*

class ApplicationUser(
    dto: ApplicationUserDto,
) {
    val id: UUID? = dto.id
    val email: String = dto.email
    val firstName: String = dto.firstName
    val lastName: String = dto.lastName
}
