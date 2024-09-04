package ro.bogdannegoita.myplannerkt.domain

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.factories.DomainFactory
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao
import java.time.LocalDateTime
import java.util.*

class ApplicationUserTest {

    private val dao = mockk<ApplicationUserDao>()
    private val domainFactory = mockk<DomainFactory>()
    private val eventPublisher = mockk<ApplicationEventPublisher>()
    private lateinit var user: ApplicationUser

    @BeforeEach
    fun setUp() {
        val userId = UUID.randomUUID()
        val userData = ApplicationUserDto(userId, "emai@email.com", "Bogdan", "Bis")
        user = ApplicationUser(userData, dao, domainFactory)
    }

    @Test
    fun `should get the acquired plans of this user sorted by title`() {
        val planDao = mockk<PlanDao>()
        val plan1Data = PlanDto(UUID.randomUUID(), "Title 1", "Description C", "RED", true,
            LocalDateTime.parse("2024-01-01T16:50:50"))
        val plan1 = Plan(plan1Data, planDao, eventPublisher)

        val plan2Data = PlanDto(UUID.randomUUID(), "Title 2", "Description B", "BLUE", true,
            LocalDateTime.parse("2024-01-02T16:50:50"))
        val plan2 = Plan(plan2Data, planDao, eventPublisher)

        val plan3Data = PlanDto(UUID.randomUUID(), "Title 3", "Description A", "GREEN", true,
            LocalDateTime.parse("2024-01-03T16:50:50"))
        val plan3 = Plan(plan3Data, planDao, eventPublisher)

        every { domainFactory.plan(plan1Data) } returns plan1
        every { domainFactory.plan(plan2Data) } returns plan2
        every { domainFactory.plan(plan3Data) } returns plan3
        every { dao.getAcquiredPlans(user.id) } returns listOf(plan2Data, plan1Data, plan3Data)

        assertEquals(3, user.acquiredPlans.size)
        assertEquals(plan1.id, user.acquiredPlans.elementAt(0).id)
        assertEquals(plan2.id, user.acquiredPlans.elementAt(1).id)
        assertEquals(plan3.id, user.acquiredPlans.elementAt(2).id)
    }

    @Test
    fun `should acquire a plan`() {
        val plan1Data = PlanDto(UUID.randomUUID(), "Title 1", "Description C", "RED", true,
            LocalDateTime.parse("2024-01-01T16:50:50"))
        val planDao = mockk<PlanDao>()
        val plan1 = Plan(plan1Data, planDao, eventPublisher)
        val plan2Data = PlanDto(UUID.randomUUID(), "Title 2", "Description B", "BLUE", true,
            LocalDateTime.parse("2024-01-02T16:50:50"))
        val plan2 = Plan(plan2Data, planDao, eventPublisher)

        every { domainFactory.plan(plan1Data) } returns plan1
        every { dao.getAcquiredPlans(user.id) } returns listOf(plan1Data)
        every { dao.acquirePlan(any(), any()) } just Runs

        user.acquirePlan(plan2)

        assertEquals(2, user.acquiredPlans.size)
    }
}
