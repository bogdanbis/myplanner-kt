package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ro.bogdannegoita.myplannerkt.api.responses.ApplicationUserResponse
import ro.bogdannegoita.myplannerkt.domain.MyPlanner
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.security.exceptions.UserNotFoundException

@RestController
class ApplicationUserController(myPlanner: MyPlanner) : BaseController(myPlanner) {

    @GetMapping("/whoami")
    fun whoami(@AuthenticationPrincipal principal: UserDetails): ApplicationUserResponse {
        try {
            val user = myPlanner.loadUser(principal.username)
            return ApplicationUserResponse(user.data)
        } catch (e: EntityNotFoundException) {
            throw UserNotFoundException()
        }
    }
}
