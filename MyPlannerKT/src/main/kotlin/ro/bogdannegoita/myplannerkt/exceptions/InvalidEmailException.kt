package ro.bogdannegoita.myplannerkt.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
class InvalidEmailException(email: String) : BaseException("Invalid email address: $email") {
    override val errorType = "invalid_email"
}
