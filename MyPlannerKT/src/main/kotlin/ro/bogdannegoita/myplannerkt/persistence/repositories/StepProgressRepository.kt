package ro.bogdannegoita.myplannerkt.persistence.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ro.bogdannegoita.myplannerkt.persistence.entities.StepProgressEntity
import java.util.*

@Repository
interface StepProgressRepository : JpaRepository<StepProgressEntity, UUID> {
    fun findAllByPlanId(id: UUID): List<StepProgressEntity>
    fun countByStepIdAndCompletedTrue(id: UUID): Int
    fun findAllByParentStepId(id: UUID): List<StepProgressEntity>
}
