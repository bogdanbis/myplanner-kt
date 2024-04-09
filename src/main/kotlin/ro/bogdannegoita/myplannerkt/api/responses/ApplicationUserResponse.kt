package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.ApplicationUser

class ApplicationUserResponse(user: ApplicationUser) {
    val id = user.id
    val email = user.email
    val firstName = user.firstName
    val lastName = user.lastName
}
