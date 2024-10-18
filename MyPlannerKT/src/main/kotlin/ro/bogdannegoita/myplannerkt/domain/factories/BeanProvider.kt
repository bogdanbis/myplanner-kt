package ro.bogdannegoita.myplannerkt.domain.factories

import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.domain.ApplicationUser
import ro.bogdannegoita.myplannerkt.persistence.daos.ApplicationUserDao

@Configuration
class BeanProvider(
    private val eventPublisher: ApplicationEventPublisher,
    private val domainProviderBeanProvider: ObjectProvider<DomainProvider>,
    private val applicationUserDaoProvider: ObjectProvider<ApplicationUserDao>,
) {
    @Bean
    @Scope("prototype")
    fun applicationUser(data: ApplicationUserDto): ApplicationUser {
        return ApplicationUser(data, applicationUserDaoProvider.getObject(),
            domainProviderBeanProvider.getObject(), eventPublisher)
    }
}
