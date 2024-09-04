package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

class Plan(
    data: PlanDto,
    private val dao: PlanDao,
    private val domainFactory: DomainFactory,
) : Comparable<Plan> {
    val id = data.id!!
    val title by data::title
    val description by data::description
    val isPublic by data::isPublic

    var authors = mutableSetOf<Author>()
        get() {
            loadAuthor()
            return field
        }
        private set

    private var loadedAuthor = false
    private fun loadAuthor() {
        if (loadedAuthor)
            return
        authors = dao.getAuthorsOf(id).map { domainFactory.author(it) }.toMutableSet()
        loadedAuthor = true
    }

    override fun compareTo(other: Plan) = title.compareTo(other.title, ignoreCase = true)
}
