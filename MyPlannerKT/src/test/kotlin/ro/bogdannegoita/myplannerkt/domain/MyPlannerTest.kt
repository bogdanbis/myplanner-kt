package ro.bogdannegoita.myplannerkt.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import java.time.LocalDateTime
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
        val plan1 = PlanDto(UUID.randomUUID(),
            "Aurora Borealis",
            "A mesmerizing natural light display in the polar sky",
            "A stunning natural phenomenon where vibrant ribbons of green, purple, and pink lights dance across the polar skies. This occurs when charged solar particles collide with Earth's magnetic field, producing a breathtaking celestial display, most commonly seen in the northern and southern regions during winter months.",
            "RED",
            true,
            LocalDateTime.parse("2024-01-01T16:50:50"),
            LocalDateTime.parse("2024-01-01T16:50:50"))
        val plan2 = PlanDto(UUID.randomUUID(),
            "Sand Dunes",
            "Rolling mounds of wind-sculpted sand",
            "Sculpted by wind over time, sand dunes form vast landscapes of gently sloping hills and sharp crests, often found in deserts and along coastal areas. These ever-changing formations provide habitats for various species and are a testament to the constant movement of Earth's surface materials.",
            "BLUE",
            true,
            LocalDateTime.parse("2024-01-02T16:50:50"),
            LocalDateTime.parse("2024-01-01T16:50:50"))
        val plan3 = PlanDto(UUID.randomUUID(),
            "Bioluminescent Plankton",
            "Tiny marine organisms that emit a soft",
            "These microscopic marine organisms light up the ocean in dazzling blue and green glows. When agitated by motion, they emit light through a chemical reaction, creating surreal, glowing waves at night. This bioluminescence serves both defensive and communicative purposes for the plankton, making coastal waters shimmer beautifully.",
            "GREEN",
            true,
            LocalDateTime.parse("2024-01-03T16:50:50"),
            LocalDateTime.parse("2024-01-01T16:50:50"))

        every { planDao.getPublicPlans() } returns listOf(plan1, plan2, plan3)

        val plans = myPlanner.getPublicPlans()

        assertEquals(plans.size, 3)
        assertEquals(plans[0].id, plan1.id)
        assertEquals(plans[1].id, plan2.id)
        assertEquals(plans[2].id, plan3.id)
    }

    @Test
    fun `should find a plan by id`() {
        val planId = UUID.randomUUID()
        val plan = PlanDto(UUID.randomUUID(),
            "Aurora Borealis",
            "A mesmerizing natural light display in the polar sky",
            "A stunning natural phenomenon where vibrant ribbons of green, purple, and pink lights dance across the polar skies. This occurs when charged solar particles collide with Earth's magnetic field, producing a breathtaking celestial display, most commonly seen in the northern and southern regions during winter months.",
            "RED",
            true,
            LocalDateTime.parse("2024-01-01T16:50:50"),
            LocalDateTime.parse("2024-01-01T16:50:50"))

        every { planDao.getById(planId) } returns plan

        assertEquals(myPlanner.getPublicPlan(planId)?.id, plan.id)
    }

    @Test
    fun `should throw exception when plan is not found`() {
        every { planDao.getById(any()) } throws EntityNotFoundException(PlanEntity::class)
        assertThrows<EntityNotFoundException> { myPlanner.getPublicPlan(UUID.randomUUID()) }
    }
}
