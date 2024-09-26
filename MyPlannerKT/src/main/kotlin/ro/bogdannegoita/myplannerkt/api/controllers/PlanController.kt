package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ro.bogdannegoita.myplannerkt.api.requests.PlanRequest
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
        return myPlanner.getPublicPlans().toSortedSet().map(::PlanSimpleResponse)
    }

    @GetMapping("/{id}")
    fun getPublicInfo(@PathVariable id: UUID): PlanSimpleResponse? {
        return myPlanner.getPublicPlan(id)?.let(::PlanSimpleResponse)
    }

    @GetMapping("/created")
    fun getCreatedPlans(@AuthenticationPrincipal principal: UserDetails): List<PlanSimpleResponse> {
        return user(principal).createdPlans.map(::PlanSimpleResponse)
    }

    @GetMapping("/created/{id}")
    fun getCreatedPlan(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID): PlanResponse? {
        return user(principal).getCreatedPlan(id)?.let(::PlanResponse)
    }

    @PostMapping("/create")
    fun createPlan(@AuthenticationPrincipal principal: UserDetails, @RequestBody request: PlanRequest)
            : PlanSimpleResponse? {
        val plan = myPlanner.createPlan(user(principal), planRequestToDto(request))
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
        val plan = user(principal).updatePlan(id, planRequestToDto(request))
        return plan?.let { PlanResponse(it) }
    }

    @DeleteMapping("/{id}")
    fun deletePlan(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID) {
        user(principal).deletePlan(id)
    }

    private fun planRequestToDto(request: PlanRequest): PlanDto = PlanDto(
        title = request.title, shortDescription = request.shortDescription,
        description = request.description, color = request.color, isPublic = request.isPublic,
        createdAt = LocalDateTime.now(), lastModifiedAt = LocalDateTime.now(),
        steps = request.steps
    )
}
