package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.StepDto
import ro.bogdannegoita.myplannerkt.commons.types.Photo
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.StepEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.StepRepository
import java.util.*

@Component
class StepDao(
    private val repository: StepRepository,
    private val photoDao: PhotoDao,
) : StepContainerDao {
    private val dtoMapper = DtoMapper()

    fun create(data: StepDto, plan: PlanEntity): StepDto {
        var entity = StepEntity(
            title = data.title,
            description = data.description,
            index = data.index,
            completedStepsCount = 0,
            plan = plan,
        )
        entity = repository.save(entity)
        data.steps?.forEach { createSubstep(it, entity) }
        return dtoMapper.stepDto(entity)
    }

    override fun addStep(containerId: UUID, data: StepDto): StepDto {
        val parentStep = findById(containerId)
        return createSubstep(data, parentStep)
    }

    private fun createSubstep(data: StepDto, parentStep: StepEntity): StepDto {
        var entity = StepEntity(
            title = data.title,
            description = data.description,
            index = data.index,
            completedStepsCount = 0,
            parentStep = parentStep,
        )
        entity = repository.save(entity)
        data.steps?.forEach { createSubstep(it, entity) }
        return dtoMapper.stepDto(entity)
    }

    fun update(id: UUID, data: StepDto) {
        val entity = findById(id)
        entity.title = data.title
        entity.description = data.description
        entity.index = data.index
        entity.completedStepsCount = data.completedStepsCount
        repository.save(entity)
    }

    fun findById(id: UUID): StepEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(StepEntity::class) }
    }

    fun findByPlanId(id: UUID): List<StepDto> {
        return repository.findAllByPlanId(id).map(dtoMapper::stepDto)
    }

    fun getSteps(id: UUID): List<StepDto> {
        return repository.findAllByParentStepId(id)
            .map { dtoMapper.stepDto(it) }
    }

    fun getImages(id: UUID): List<Photo> {
        return findById(id).images.map(dtoMapper::photo)
    }

    fun uploadImage(id: UUID, photo: Photo): Photo {
        val step = findById(id)
        return photoDao.upload(photo, step)
    }

    override fun removeStep(id: UUID) {
        repository.deleteById(id)
    }
}
