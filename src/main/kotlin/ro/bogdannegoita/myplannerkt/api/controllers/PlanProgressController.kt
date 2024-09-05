package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.bogdannegoita.myplannerkt.api.responses.PlanProgressResponse
import ro.bogdannegoita.myplannerkt.domain.MyPlanner
import java.util.*

@RestController
@RequestMapping("/plans/acquired")
class PlanProgressController(myPlanner: MyPlanner) : BaseController(myPlanner) {

    @GetMapping
    fun getAcquiredPlans(@AuthenticationPrincipal principal: UserDetails): List<PlanProgressResponse> {
        return user(principal).acquiredPlans.map(::PlanProgressResponse)
    }

    @GetMapping("/{id}")
    fun getPlanProgress(
        @AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID
    ): PlanProgressResponse? {
        return user(principal).getAcquiredPlan(id)?.let { PlanProgressResponse(it) }
    }
}
