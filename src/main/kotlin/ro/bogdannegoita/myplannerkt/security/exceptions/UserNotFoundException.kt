package ro.bogdannegoita.myplannerkt.security.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import ro.bogdannegoita.myplannerkt.domain.ApplicationUser
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class UserNotFoundException : EntityNotFoundException(ApplicationUser::class) {
    override val errorType = "user_not_found"
}
