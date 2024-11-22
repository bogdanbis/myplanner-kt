package ro.bogdannegoita.myplannerkt.security.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import ro.bogdannegoita.myplannerkt.exceptions.BaseException

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class TokenExpiredException : BaseException("Token expired.") {
    override val errorType = "token_expired"
}
