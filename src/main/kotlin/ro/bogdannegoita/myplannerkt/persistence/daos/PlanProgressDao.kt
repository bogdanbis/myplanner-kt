package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanProgressEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanProgressRepository
import java.time.LocalDateTime

@Component
class PlanProgressDao(private val repository: PlanProgressRepository) {
    private val dtoMapper = DtoMapper()

    fun create(planEntity: PlanEntity, userEntity: ApplicationUserEntity): PlanProgressDto {
        val entity = PlanProgressEntity(LocalDateTime.now(), planEntity, userEntity)
        return dtoMapper.planProgressDto(repository.save(entity))
    }
}
