package ro.bogdannegoita.myplannerkt.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
abstract class BaseException(message: String?) : RuntimeException(message) {
    abstract val errorType: String
}
