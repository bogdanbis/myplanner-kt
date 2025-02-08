package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ro.bogdannegoita.myplannerkt.api.responses.ApplicationUserResponse
import ro.bogdannegoita.myplannerkt.api.responses.PlanInviteResponse
import ro.bogdannegoita.myplannerkt.commons.types.UserUIPreferences
import ro.bogdannegoita.myplannerkt.domain.MyPlanner
import ro.bogdannegoita.myplannerkt.exceptions.EntityNotFoundException
import ro.bogdannegoita.myplannerkt.security.exceptions.UserNotFoundException
import java.util.*

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

    @GetMapping("/invites/received")
    fun getPendingInvites(@AuthenticationPrincipal principal: UserDetails): List<PlanInviteResponse> {
        return user(principal).receivedInvites
            .map { PlanInviteResponse(it) }
    }

    @PostMapping("/invites/pending/{id}/accept")
    fun acceptInvite(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID
    ) {
        user(principal).acceptInvite(id)
    }

    @PostMapping("/invites/pending/{id}/decline")
    fun declinetInvite(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID
    ) {
        user(principal).declineInvite(id)
    }

    @GetMapping("/invites/sent")
    fun getSentInvites(@AuthenticationPrincipal principal: UserDetails): List<PlanInviteResponse> {
        return user(principal).sentInvites
            .map { PlanInviteResponse(it) }
    }

    @GetMapping("/ui-preferences")
    fun getUIPreferences(@AuthenticationPrincipal principal: UserDetails): UserUIPreferences {
        return user(principal).uiPreferences
    }

    @PutMapping("/ui-preferences")
    fun updateUIPreferences(
        @AuthenticationPrincipal principal: UserDetails,
        @RequestBody preferences: UserUIPreferences
    ) {
        user(principal).updateUIPreferences(preferences)
    }
}
