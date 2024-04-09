package ro.bogdannegoita.myplannerkt.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import kotlin.test.assertEquals

@SpringBootTest
class MyPlannerTest @Autowired constructor(
    domainProvider: ObjectProvider<ApplicationUser>
) {

    private val userDao = mockk<ApplicationUserDao>()
    private val myPlanner = MyPlanner(domainProvider, userDao)

    @Test
    fun `should load the user with given email`() {
        val email = "example@email.com"
        val dto = ApplicationUserDto(null, email, "Bogdan", "Negoita")
        every { userDao.findByEmail(email) } returns dto

        val user = myPlanner.loadUser(email)

        assertEquals(user.email, email)
        assertEquals(user.firstName, "Bogdan")
        assertEquals(user.lastName, "Negoita")
    }

    @Test
    fun `should cache the user`() {
        val email = "example@email.com"
        val dto = ApplicationUserDto(null, email, "Bogdan", "Negoita")
        every { userDao.findByEmail(email) } returns dto

        myPlanner.loadUser(email)
        myPlanner.loadUser(email)

        verify(exactly = 1) { userDao.findByEmail(email) }
    }
}
