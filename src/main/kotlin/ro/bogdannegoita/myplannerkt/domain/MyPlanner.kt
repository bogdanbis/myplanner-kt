package ro.bogdannegoita.myplannerkt.domain

import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.domain.factories.myPlannerCache
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.util.*

@Component
class MyPlanner(
    private val domainProvider: ObjectProvider<ApplicationUser>,
    private val userDao: ApplicationUserDao,
    private val planDao: PlanDao,
    private val domainFactory: DomainFactory
) {

    private val users = myPlannerCache<String, ApplicationUser>()
    private var publicPlans = mutableListOf<Plan>()
    private val plansById by domainFactory.registry::publicPlans

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
        loadPublicPlans()
        return publicPlans
    }

    fun getPlanById(id: UUID): Plan? {
        loadPlan(id)
        return plansById[id]
    }

    fun createPlan(users: Collection<ApplicationUser>, planData: PlanDto): Plan {
        val persistedPlanData = planDao.createPlan(planData, users.map(ApplicationUser::id))
        val plan = domainFactory.plan(persistedPlanData)
        if (plan.isPublic)
            publicPlans.add(plan)
        return plan
    }

    private var loadedPublicPlans = false
    private fun loadPublicPlans() {
        if (loadedPublicPlans) return
        publicPlans = planDao.getPublicPlans().map(domainFactory::plan).toMutableList()
        loadedPublicPlans = true
    }

    private fun loadPlan(id: UUID) {
        if (plansById[id] != null)
            return
        val planDto = planDao.getById(id)
        plansById[id] = domainFactory.plan(planDto)
    }

}
