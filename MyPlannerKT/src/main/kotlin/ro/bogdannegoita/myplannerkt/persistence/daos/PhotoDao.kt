package ro.bogdannegoita.myplannerkt.persistence.daos

import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.commons.types.Photo
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.persistence.entities.PhotoEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.PlanEntity
import ro.bogdannegoita.myplannerkt.persistence.entities.StepEntity
import ro.bogdannegoita.myplannerkt.persistence.mappers.DtoMapper
import ro.bogdannegoita.myplannerkt.persistence.repositories.PhotoRepository
import java.util.*

@Component
class PhotoDao(private val repository: PhotoRepository) {

    private val dtoMapper = DtoMapper()

    fun findById(id: UUID): Photo {
        val entity = findEntityById(id)
        val photo = dtoMapper.photo(entity)
        photo.content = entity.content
        return photo
    }

    fun upload(data: Photo, plan: PlanEntity): Photo {
        var photo = PhotoEntity(name = data.name,
            contentType = data.contentType,
            content = data.content,
            plan = plan)
        photo = repository.save(photo)
        return dtoMapper.photo(photo)
    }

    fun upload(data: Photo, step: StepEntity): Photo {
        var photo = PhotoEntity(name = data.name,
            contentType = data.contentType,
            content = data.content,
            step = step)
        photo = repository.save(photo)
        return dtoMapper.photo(photo)
    }

    fun delete(id: UUID) {
        repository.deleteById(id)
    }

    private fun findEntityById(id: UUID): PhotoEntity {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException(PhotoEntity::class) }
    }
}
