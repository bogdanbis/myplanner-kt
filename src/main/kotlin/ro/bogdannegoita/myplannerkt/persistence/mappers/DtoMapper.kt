package ro.bogdannegoita.myplannerkt.persistence.mappers

import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanProgressEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.TaskEntity

class DtoMapper {
    fun applicationUserDto(entity: ApplicationUserEntity): ApplicationUserDto {
        return ApplicationUserDto(entity.id, entity.email!!, entity.firstName!!, entity.lastName!!)
    }

    fun planDto(entity: PlanEntity): PlanDto {
        return PlanDto(entity.id, entity.title!!, entity.description!!, entity.color!!, entity.isPublic!!,
            entity.createdAt!!)
    }

    fun taskDto(entity: TaskEntity): TaskDto {
        return TaskDto(entity.id, entity.title!!, entity.description!!, entity.index!!)
    }

    fun planProgressDto(entity: PlanProgressEntity): PlanProgressDto {
        return PlanProgressDto(entity.id, planDto(entity.plan!!), entity.acquiredAt!!)
    }
}
