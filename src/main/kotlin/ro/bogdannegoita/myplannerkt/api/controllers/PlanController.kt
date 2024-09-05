package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ro.bogdannegoita.myplannerkt.api.requests.PlanRequest
import ro.bogdannegoita.myplannerkt.api.requests.TasksRequest
import ro.bogdannegoita.myplannerkt.api.responses.PlanProgressResponse
import ro.bogdannegoita.myplannerkt.api.responses.PlanResponse
import ro.bogdannegoita.myplannerkt.api.responses.PlanSimpleResponse
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.domain.MyPlanner
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/plans")
class PlanController(myPlanner: MyPlanner) : BaseController(myPlanner) {

    @GetMapping("/browse")
    fun getPublicPlans(): List<PlanSimpleResponse> {
        return myPlanner.getPublicPlans().map(::PlanSimpleResponse)
    }

    @GetMapping("/{id}")
    fun getPlanById(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID): PlanResponse? {
        return myPlanner.getPlan(user(principal), id)?.let(::PlanResponse)
    }

    @GetMapping("/created")
    fun getCreatedPlans(@AuthenticationPrincipal principal: UserDetails): List<PlanSimpleResponse> {
        return user(principal).createdPlans.map(::PlanSimpleResponse)
    }

    @PostMapping("/create")
    fun createPlan(@AuthenticationPrincipal principal: UserDetails, @RequestBody request: PlanRequest)
            : PlanSimpleResponse? {
        val planData = PlanDto(title = request.title, description = request.description, color = request.color,
            isPublic = request.isPublic, createdAt = LocalDateTime.now())
        val plan = myPlanner.createPlan(user(principal), planData)
        return PlanSimpleResponse(plan)
    }

    @PostMapping("/{id}/acquire")
    fun acquirePlan(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID): PlanProgressResponse? {
        val plan = myPlanner.getPublicPlan(id) ?: return null
        return user(principal).acquirePlan(plan)?.let { PlanProgressResponse(it) }
    }

    @PutMapping("/{id}")
    fun updatePlan(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @RequestBody request: PlanRequest
    ): PlanResponse? {
        val planData = PlanDto(title = request.title, description = request.description, color = request.color,
            isPublic = request.isPublic, createdAt = LocalDateTime.now())
        val plan = user(principal).updatePlan(id, planData)
        return PlanResponse(plan)
    }

    // TDOO: most likely should be included in updatePlan
    @PostMapping("/{id}/add-tasks")
    fun addTasks(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @RequestBody request: TasksRequest
    ): PlanResponse? {
        val plan = user(principal).getCreatedPlan(id) ?: return null
        plan.addTasks(request.tasks)
        return PlanResponse(plan)
    }
}
