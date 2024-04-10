package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Author

class AuthorResponse(author: Author) {
    var id = author.id
    var firstName = author.firstName
    var lastName = author.lastName
    var email = author.email
}
