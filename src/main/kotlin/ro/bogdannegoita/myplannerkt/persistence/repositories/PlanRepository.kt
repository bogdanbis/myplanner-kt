package ro.bogdannegoita.myplannerkt.persistence.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import java.util.*

@Repository
interface PlanRepository : JpaRepository<PlanEntity, UUID> {
    fun findByIsPublicTrue(): List<PlanEntity>
}
