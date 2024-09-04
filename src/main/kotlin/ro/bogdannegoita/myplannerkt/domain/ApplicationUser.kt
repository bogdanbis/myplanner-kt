package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.AuthorDto
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

    var plans: SortedSet<Plan> = sortedSetOf()
        get() {
            loadPlans()
            return field
        }
        private set

    fun createAuthor(): Author {
        loadAuthor()
        if (author != null)
            return author!!
        val data = AuthorDto(firstName = firstName, lastName = lastName, email = email)
        val persistedAuthorData = dao.createAuthor(data, id)
        author = domainFactory.author(persistedAuthorData)
        return author!!
    }

    fun addPlan(plan: Plan) {
        if (plans.contains(plan))
            return
        plans.add(plan)
        dao.addPlan(id, plan.id)
    }

    private fun loadAuthor() {
        if (loadedAuthor)
            return
        author = dao.getAuthor(id)?.let { domainFactory.author(it) }
        loadedAuthor = true
    }

    private fun loadPlans() {
        if (loadedPlans)
            return
        plans = dao.getPlans(id).map { domainFactory.plan(it) }
            .toSortedSet()
        loadedPlans = true
    }

    private var loadedAuthor = false
    private var loadedPlans = false
}
