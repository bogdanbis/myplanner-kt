package ro.bogdannegoita.myplannerkt.persistence.mappers

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity

class DtoMapper {
    fun applicationUserDto(entity: ApplicationUserEntity): ApplicationUserDto {
        return ApplicationUserDto(entity.id, entity.email!!, entity.firstName!!, entity.lastName!!)
    }

    fun planDto(entity: PlanEntity): PlanDto {
        return PlanDto(entity.id, entity.title!!, entity.description!!, entity.color!!, entity.isPublic!!,
            entity.createdAt!!)
    }
}
