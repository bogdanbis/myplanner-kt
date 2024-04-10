package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.AuthorDto

class Author(data: AuthorDto) : ApplicationUser(data.userDto)
