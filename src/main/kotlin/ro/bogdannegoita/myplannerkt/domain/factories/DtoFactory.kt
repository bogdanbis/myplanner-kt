package ro.bogdannegoita.myplannerkt.domain.factories

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity

class DtoFactory {
    companion object {
        fun applicationUserDto(entity: ApplicationUserEntity): ApplicationUserDto {
            return ApplicationUserDto(entity.id, entity.email!!, entity.firstName!!, entity.lastName!!)
        }
    }
}
