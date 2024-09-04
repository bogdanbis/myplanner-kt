package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.domain.ApplicationUser
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao
import ro.bogdannegoita.myplannerkt.persistence.daos.PlanDao

@Configuration
class BeanProvider(
    private val factoryProvider: ObjectProvider<DomainFactory>,
    private val applicationUserDaoProvider: ObjectProvider<ApplicationUserDao>,
    private val planDaoProvider: ObjectProvider<PlanDao>,
) {
    @Bean
    @Scope("prototype")
    fun applicationUser(data: ApplicationUserDto): ApplicationUser {
        return ApplicationUser(data, applicationUserDaoProvider.getObject(), planDaoProvider.getObject(),
            factoryProvider.getObject())
    }
}
