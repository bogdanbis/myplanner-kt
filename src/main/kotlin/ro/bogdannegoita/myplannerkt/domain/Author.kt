package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.AuthorDto

class Author(data: AuthorDto) {
    val id = data.id!!
    val firstName by data::firstName
    val lastName by data::lastName
    val email by data::email
}
