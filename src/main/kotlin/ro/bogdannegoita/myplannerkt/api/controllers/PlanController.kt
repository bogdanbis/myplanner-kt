package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ro.bogdannegoita.myplannerkt.api.requests.CreatePlanRequest
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

    @PostMapping("/create-plan")
    fun createPlan(@AuthenticationPrincipal principal: UserDetails, @RequestBody request: CreatePlanRequest)
            : PlanResponse? {
        val planData = PlanDto(title = request.title, description = request.description, isPublic = request.isPublic)
        val plan = myPlanner.createPlan(listOf(user(principal)), planData)
        return PlanResponse(plan)
    }

    @GetMapping("/{id}")
    fun getPlanById(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID): PlanResponse? {
        return myPlanner.getPlanById(id)?.let { PlanResponse(it) }
    }

    @PostMapping("/{id}/acquire")
    fun acquirePlan(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID) {
        val plan = myPlanner.getPlanById(id) ?: return
        user(principal).addPlan(plan)
    }
}
