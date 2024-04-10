package ro.bogdannegoita.myplannerkt.domain

import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.domain.factories.myPlannerCache
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

@Component
class MyPlanner(
    private val domainProvider: ObjectProvider<ApplicationUser>,
    private val userDao: ApplicationUserDao,
    private val planDao: PlanDao,
    private val domainFactory: DomainFactory
) {
    private val users = myPlannerCache<String, ApplicationUser>()
    private var publicPlans = mutableListOf<Plan>()

    fun loadUser(email: String): ApplicationUser {
        var user = users.getOrNull(email)
        if (user != null)
            return user
        val userData = userDao.findByEmail(email)
        user = domainProvider.getObject(userData)
        users[email] = user
        return user
    }

    fun getPublicPlans(): List<Plan> {
        if (loadedPublicPlans)
            return publicPlans
        publicPlans = planDao.getPublicPlans().map(domainFactory::plan).toMutableList()
        loadedPublicPlans = true
        return publicPlans
    }

    fun createPlan(users: Collection<ApplicationUser>, planData: PlanDto): Plan {
        val authors = users.map { user -> user.author ?: createAuthor(user) }
        val persistedPlanData = planDao.createPlan(planData, authors.map(Author::id))
        val plan = domainFactory.plan(persistedPlanData)
        if (plan.isPublic)
            publicPlans.add(plan)
        return plan
    }

    fun createAuthor(user: ApplicationUser): Author {
        TODO()
    }

    private var loadedPublicPlans = false
}
