package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.dtos.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.types.InviteStatus
import ro.bogdannegoita.myplannerkt.commons.dtos.PlanDto
import ro.bogdannegoita.myplannerkt.commons.dtos.PlanInviteDto
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanInviteEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanInviteRespository
import java.time.LocalDateTime
import java.util.*

@Component
class PlanInviteDao(private val repository: PlanInviteRespository) {
    private val dtoMapper = DtoMapper()

    fun create(plan: PlanEntity, sender: ApplicationUserEntity, recipient: ApplicationUserEntity): PlanInviteDto {
        val entity = PlanInviteEntity(
            status = InviteStatus.PENDING,
            createdAt = LocalDateTime.now(),
            plan = plan,
            sender = sender,
            recipient = recipient,
        )
        return dtoMapper.planInviteDto(repository.save(entity))
    }

    fun getPlan(id: UUID): PlanDto {
        return dtoMapper.planDto(findEntityById(id).plan!!)
    }

    fun getSender(id: UUID): ApplicationUserDto {
        return dtoMapper.applicationUserDto(findEntityById(id).sender!!)
    }

    fun getRecipient(id: UUID): ApplicationUserDto {
        val recipient = findEntityById(id).recipient!!
        return dtoMapper.applicationUserDto(recipient)
    }

    fun findBySender(id: UUID): List<PlanInviteDto> {
        return repository.findBySenderId(id).map { dtoMapper.planInviteDto(it) }
    }

    fun findByRecipient(id: UUID): List<PlanInviteDto> {
        return repository.findByRecipientId(id).map { dtoMapper.planInviteDto(it) }
    }

    fun updateStatus(id: UUID, status: InviteStatus, respondedAt: LocalDateTime) {
        val invite = findEntityById(id)
        invite.status = status
        invite.respondedAt = respondedAt
        repository.save(invite)
    }

    private fun findEntityById(id: UUID): PlanInviteEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(PlanInviteEntity::class) }
    }
}
