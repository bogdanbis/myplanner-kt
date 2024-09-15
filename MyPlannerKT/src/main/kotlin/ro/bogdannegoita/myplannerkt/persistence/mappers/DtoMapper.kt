package ro.bogdannegoita.myplannerkt.persistence.mappers

import ro.bogdannegoita.myplannerkt.commons.*
import ro.bogdannegoita.myplannerkt.persistence.entities.*

class DtoMapper {
    fun applicationUserDto(entity: ApplicationUserEntity): ApplicationUserDto {
        return ApplicationUserDto(entity.id, entity.email!!, entity.firstName!!, entity.lastName!!)
    }

    fun planDto(entity: PlanEntity): PlanDto {
        return PlanDto(entity.id, entity.title!!, entity.shortDescription!!, entity.description!!,
            entity.color!!, entity.isPublic!!, entity.createdAt!!, entity.lastModifiedAt!!)
    }

    fun taskDto(entity: TaskEntity): TaskDto {
        return TaskDto(entity.id, entity.title!!, entity.description!!, entity.index!!)
    }

    fun planProgressDto(entity: PlanProgressEntity): PlanProgressDto {
        return PlanProgressDto(entity.id, planDto(entity.plan!!), entity.acquiredAt!!)
    }

    fun taskProgressDto(entity: TaskProgressEntity): TaskProgressDto {
        return TaskProgressDto(entity.id, entity.completed!!, taskDto(entity.task!!))
    }
}
