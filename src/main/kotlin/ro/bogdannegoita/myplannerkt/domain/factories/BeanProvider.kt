package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.domain.ApplicationUser
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao

@Configuration
class BeanProvider(
    private val domainFactory: DomainFactory,
    private val applicationUserDao: ApplicationUserDao,
) {
    @Bean
    @Scope("prototype")
    fun applicationUser(data: ApplicationUserDto): ApplicationUser {
        return ApplicationUser(data, applicationUserDao, domainFactory)
    }
}
