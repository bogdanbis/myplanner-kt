package ro.bogdannegoita.myplannerkt.persistence.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ro.bogdannegoita.myplannerkt.persistence.entities.StepEntity
import java.util.*

interface StepRepository : JpaRepository<StepEntity, UUID> {
    fun findAllByPlanId(id: UUID): List<StepEntity>
    fun findAllByParentStepId(id: UUID): List<StepEntity>
}
