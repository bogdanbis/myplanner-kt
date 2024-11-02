package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto

class ApplicationUserResponse(data: ApplicationUserDto) {
    val id = data.id
    val email = data.email
    val firstName = data.firstName
    val lastName = data.lastName
    val name = data.firstName + " " + data.lastName
}
