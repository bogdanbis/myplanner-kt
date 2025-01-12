package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.PlanInviteDto
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.domain.types.UserUIPreferences
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.ApplicationUserRepository
import java.util.*

@Component
class ApplicationUserDao(
    private val repository: ApplicationUserRepository,
    private val planDao: PlanDao,
    private val planProgressDao: PlanProgressDao,
    private val planInviteDao: PlanInviteDao,
) {
    private val dtoMapper = DtoMapper()

    fun findByEmail(email: String): ApplicationUserDto {
        val entity = findEntityByEmail(email)
        return dtoMapper.applicationUserDto(entity)
    }

    fun findById(id: UUID): ApplicationUserEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(ApplicationUserEntity::class) }
    }

    fun acquirePlan(userId: UUID, planId: UUID): PlanProgressDto {
        val userEntity = findById(userId)
        val planEntity = planDao.findById(planId)
        val planProgressDto = planProgressDao.create(planEntity, userEntity)
        return planProgressDto
    }

    fun createPlan(data: PlanDto, authorId: UUID): PlanDto {
        return planDao.create(data, findById(authorId))
    }

    fun getAcquiredPlans(id: UUID): List<PlanProgressDto> {
        return findById(id).acquiredPlans.map(dtoMapper::planProgressDto)
    }

    fun getCreatedPlans(id: UUID): List<PlanDto> {
        return findById(id).createdPlans.map(dtoMapper::planDto)
    }

    fun deletePlan(id: UUID) {
        planDao.delete(id)
    }

    fun updateUIPreferences(id: UUID, preferences: UserUIPreferences) {
        val entity = findById(id)
        entity.uiPreferences!!.pinnedPlans = preferences.pinnedPlans
        repository.save(entity)
    }

    fun inviteUser(planId: UUID, senderEmail: String, recipientEmail: String): PlanInviteDto {
        val recipient = findEntityByEmail(recipientEmail)
        val sender = findEntityByEmail(senderEmail)
        val plan = planDao.findById(planId)
        return planInviteDao.create(plan, sender, recipient)
    }

    fun userHasPlan(email: String, planId: UUID): Boolean {
        val user = findEntityByEmail(email)
        return user.acquiredPlans.find { it.id == planId } != null
    }

    fun getSentInvites(id: UUID): List<PlanInviteDto> {
        return planInviteDao.findBySender(id)
    }

    fun getReceivedInvites(id: UUID): List<PlanInviteDto> {
        return planInviteDao.findByRecipient(id)
    }

    private fun findEntityByEmail(email: String): ApplicationUserEntity {
        return repository.findByEmail(email)
            ?: throw EntityNotFoundException(ApplicationUserEntity::class)
    }
}
