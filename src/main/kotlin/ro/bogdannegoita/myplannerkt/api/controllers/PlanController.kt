package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ro.bogdannegoita.myplannerkt.api.requests.PlanRequest
import ro.bogdannegoita.myplannerkt.api.responses.PlanResponse
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.MyPlanner
import java.util.*

@RestController
@RequestMapping("/plans")
class PlanController(myPlanner: MyPlanner) : BaseController(myPlanner) {

    @GetMapping("/browse")
    fun getPublicPlans(): List<PlanResponse> {
        return myPlanner.getPublicPlans().map(::PlanResponse)
    }

    @GetMapping("/acquired")
    fun getAcquiredPlans(@AuthenticationPrincipal principal: UserDetails): List<PlanResponse> {
        return user(principal).acquiredPlans.map(::PlanResponse)
    }

    @GetMapping("/created")
    fun getCreatedPlans(@AuthenticationPrincipal principal: UserDetails): List<PlanResponse> {
        return user(principal).createdPlans.map(::PlanResponse)
    }

    @PostMapping("/create")
    fun createPlan(@AuthenticationPrincipal principal: UserDetails, @RequestBody request: PlanRequest)
            : PlanResponse? {
        val planData = PlanDto(title = request.title, description = request.description, color = request.color,
            isPublic = request.isPublic)
        val plan = myPlanner.createPlan(user(principal), planData)
        return PlanResponse(plan)
    }

    @GetMapping("/{id}")
    fun getPlanById(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID): PlanResponse? {
        return myPlanner.getPlanById(id)?.let { PlanResponse(it) }
    }

    @PostMapping("/{id}/acquire")
    fun acquirePlan(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID) {
        val plan = myPlanner.getPlanById(id) ?: return
        user(principal).acquirePlan(plan)
    }

    @PutMapping("/{id}")
    fun updatePlan(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @RequestBody request: PlanRequest
    ): PlanResponse? {
        val planData = PlanDto(title = request.title, description = request.description, color = request.color,
            isPublic = request.isPublic)
        val plan = user(principal).updatePlan(id, planData)
        return PlanResponse(plan)
    }
}
