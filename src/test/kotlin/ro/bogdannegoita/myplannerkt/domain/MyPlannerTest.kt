package ro.bogdannegoita.myplannerkt.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest
class MyPlannerTest @Autowired constructor(
    domainProvider: ObjectProvider<ApplicationUser>,
    domainFactory: DomainFactory,
) {
    private val userDao = mockk<ApplicationUserDao>()
    private val planDao = mockk<PlanDao>()
    private val myPlanner = MyPlanner(domainProvider, userDao, planDao, domainFactory)

    @Test
    fun `should load the user with given email`() {
        val email = "example@email.com"
        val dto = ApplicationUserDto(UUID.randomUUID(), email, "Bogdan", "Negoita")
        every { userDao.findByEmail(email) } returns dto

        val user = myPlanner.loadUser(email)

        assertEquals(user.id, dto.id)
    }

    @Test
    fun `should cache the user`() {
        val email = "example@email.com"
        val dto = ApplicationUserDto(UUID.randomUUID(), email, "Bogdan", "Negoita")
        every { userDao.findByEmail(email) } returns dto

        myPlanner.loadUser(email)
        myPlanner.loadUser(email)

        verify(exactly = 1) { userDao.findByEmail(email) }
    }

    @Test
    fun `should get the public plans`() {
        val plan1 = PlanDto(UUID.randomUUID(), "Title 1", "Description 1", true)
        val plan2 = PlanDto(UUID.randomUUID(), "Title 2", "Description 2", true)
        val plan3 = PlanDto(UUID.randomUUID(), "Title 3", "Description 3", true)
        every { planDao.getPublicPlans() } returns listOf(plan1, plan2, plan3)

        val plans = myPlanner.getPublicPlans()

        assertEquals(plans.size, 3)
        assertEquals(plans[0].id, plan1.id)
        assertEquals(plans[1].id, plan2.id)
        assertEquals(plans[2].id, plan3.id)
    }
}
