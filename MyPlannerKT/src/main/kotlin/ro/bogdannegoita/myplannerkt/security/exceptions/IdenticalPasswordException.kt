package ro.bogdannegoita.myplannerkt.security.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import ro.bogdannegoita.myplannerkt.exceptions.BaseException

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
class IdenticalPasswordException(message: String?) : BaseException(message) {
    override val errorType = "identical_password"
}
