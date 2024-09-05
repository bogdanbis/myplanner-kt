package ro.bogdannegoita.myplannerkt.persistence.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanProgressEntity
import java.util.*

@Repository
interface PlanProgressRepository : JpaRepository<PlanProgressEntity, UUID>
