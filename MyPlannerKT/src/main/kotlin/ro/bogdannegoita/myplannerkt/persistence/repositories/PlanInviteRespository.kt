package ro.bogdannegoita.myplannerkt.persistence.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanInviteEntity
import java.util.*

@Repository
interface PlanInviteRespository : JpaRepository<PlanInviteEntity, UUID> {
    fun findBySenderId(id: UUID): List<PlanInviteEntity>
    fun findByRecipientId(id: UUID): List<PlanInviteEntity>
}
