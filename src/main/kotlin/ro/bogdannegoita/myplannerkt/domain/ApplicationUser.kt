package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import java.util.*

class ApplicationUser(
    data: ApplicationUserDto,
    private val dao: ApplicationUserDao,
    private val domainFactory: DomainFactory,
) {
    val id: UUID = data.id!!
    val email: String = data.email
    val firstName: String = data.firstName
    val lastName: String = data.lastName
    var author: Author? = null
        get() {
            loadAuthor()
            return field
        }
        private set

    private fun loadAuthor() {
        if (loadedAuthor)
            return
        author = dao.getAuthor(id)?.let { domainFactory.author(it) }
        loadedAuthor = true
    }

    private var loadedAuthor = false
}
