package ro.bogdannegoita.myplannerkt.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
open class EntityNotFoundException(clazz: KClass<*>) : BaseException("Cannot find ${clazz.simpleName}") {
    override val errorType = "entity_not_found"
}
