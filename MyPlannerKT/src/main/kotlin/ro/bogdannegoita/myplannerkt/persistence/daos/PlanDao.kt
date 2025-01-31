package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.ApplicationUserDto
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto
import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.commons.types.Photo
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.ApplicationUserEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PlanRepository
import java.util.*

@Component
class PlanDao(
    private val repository: PlanRepository,
    private val stepDao: StepDao,
    private val planProgressDao: PlanProgressDao,
    private val photoDao: PhotoDao,
) : StepContainerDao {
    private val dtoMapper = DtoMapper()

    fun getPublicPlans(): List<PlanDto> {
        return repository.findFirst50ByIsPublicTrueOrderByLastModifiedAtDesc()
            .map(dtoMapper::planDto)
    }

    fun getAuthor(planId: UUID): ApplicationUserDto {
        return findById(planId).author?.let { dtoMapper.applicationUserDto(it) }!!
    }

    fun getById(id: UUID): PlanDto {
        return dtoMapper.planDto(findById(id))
    }

    fun findById(id: UUID): PlanEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(PlanEntity::class) }
    }

    fun findByTitle(title: String): List<PlanDto> {
        return repository.findByTitleContainsIgnoreCase(title).map(dtoMapper::planDto)
    }

    fun create(data: PlanDto, author: ApplicationUserEntity): PlanDto {
        var entity = PlanEntity(
            title = data.title,
            shortDescription = data.shortDescription,
            description = data.description,
            color = data.color,
            isPublic = data.isPublic,
            createdAt = data.createdAt,
            lastModifiedAt = data.createdAt,
            author = author,
        )
        entity = repository.save(entity)
        data.steps?.forEach { stepDao.create(it, entity) }
        return dtoMapper.planDto(entity)
    }

    fun update(id: UUID, data: PlanDto) {
        val entity = findById(id)
        entity.title = data.title
        entity.shortDescription = data.shortDescription
        entity.description = data.description
        entity.color = data.color
        entity.isPublic = data.isPublic
        entity.lastModifiedAt = data.lastModifiedAt
        repository.save(entity)
    }

    fun getSteps(id: UUID): Collection<StepDto> {
        return stepDao.findByPlanId(id)
    }

    override fun addStep(containerId: UUID, data: StepDto): StepDto {
        val planEntity = findById(containerId)
        return stepDao.create(data, planEntity)
    }

    fun delete(id: UUID) {
        repository.deleteById(id)
    }

    fun getAcquiredPlans(id: UUID): List<PlanProgressDto> {
        return planProgressDao.findByPlanId(id)
    }

    fun getNumberOfAcquiredPlans(id: UUID): Int {
        return planProgressDao.countByPlan(id)
    }

    override fun removeStep(id: UUID) {
        stepDao.removeStep(id)
    }

    fun getImages(id: UUID): List<Photo> {
        return findById(id).images.map(dtoMapper::photo)
    }

    fun uploadImage(id: UUID, photo: Photo): Photo {
        val plan: PlanEntity = findById(id)
        return photoDao.upload(photo, plan)
    }

    fun deleteImage(imageId: UUID) {
        photoDao.delete(imageId)
    }
}
