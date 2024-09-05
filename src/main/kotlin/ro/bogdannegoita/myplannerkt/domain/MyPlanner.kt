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
    private val domainFactory: DomainFactory,
) {

    private val users = myPlannerCache<String, ApplicationUser>()
    private val publicPlansRegistry by domainFactory.registry::publicPlans

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
        return publicPlansRegistry.values.toList()
    }

    fun getPublicPlan(id: UUID): Plan? {
        loadPlan(id)
        return publicPlansRegistry[id]
    }

    fun getPlan(user: ApplicationUser, id: UUID): Plan? {
        return getPublicPlan(id) ?: user.getCreatedPlan(id)
    }

    fun createPlan(user: ApplicationUser, planData: PlanDto): Plan {
        val plan = user.createPlan(planData)
        if (plan.isPublic)
            publishPlan(plan)
        return plan
    }

    fun publishPlan(plan: Plan) {
        publicPlansRegistry[plan.id] = plan
    }

    fun unpublishPlan(plan: Plan) {
        publicPlansRegistry.remove(plan.id)
    }

    private var loadedPublicPlans = false
    private fun loadPublicPlans() {
        if (loadedPublicPlans) return
        planDao.getPublicPlans()
            .map {
                val plan = domainFactory.plan(it)
                publicPlansRegistry[plan.id] = plan
                plan
            }
            .toMutableList()
        loadedPublicPlans = true
    }

    private fun loadPlan(id: UUID) {
        if (publicPlansRegistry[id] != null)
            return
        val planDto = planDao.getById(id)
        if (planDto.isPublic)
            publicPlansRegistry[id] = domainFactory.plan(planDto)
    }

}
