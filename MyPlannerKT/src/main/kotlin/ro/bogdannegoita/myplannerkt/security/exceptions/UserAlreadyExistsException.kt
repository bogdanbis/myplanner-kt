package ro.bogdannegoita.myplannerkt.security.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import ro.bogdannegoita.myplannerkt.exceptions.BaseException

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
class UserAlreadyExistsException(email: String) : BaseException("The email address $email is already used.") {
    override val errorType = "email_used"
}
