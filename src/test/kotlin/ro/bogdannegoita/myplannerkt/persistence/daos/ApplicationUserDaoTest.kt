package ro.bogdannegoita.myplannerkt.persistence.daos

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.repositories.ApplicationUserRepository
import kotlin.test.assertEquals

class ApplicationUserDaoTest {

    private val repository = mockk<ApplicationUserRepository>()
    private val planDao = mockk<PlanDao>()
    private val dao: ApplicationUserDao = ApplicationUserDao(repository, planDao)

    @Test
    fun findByEmail() {
        val email = "example@email.com"

        every { repository.findByEmail(email) } returns
                ApplicationUserEntity(email = email, firstName = "Bogdan", lastName = "Negoita")

        assertEquals(email, dao.findByEmail(email).email)
    }
}
