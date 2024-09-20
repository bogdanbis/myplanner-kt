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

    fun stepDto(entity: StepEntity): StepDto {
        return StepDto(entity.id, entity.title!!, entity.description!!, entity.index!!)
    }

    fun planProgressDto(entity: PlanProgressEntity): PlanProgressDto {
        return PlanProgressDto(entity.id, planDto(entity.plan!!), entity.acquiredAt!!)
    }

    fun stepProgressDto(entity: StepProgressEntity): StepProgressDto {
        return StepProgressDto(entity.id, entity.completed!!, stepDto(entity.step!!))
    }
}
