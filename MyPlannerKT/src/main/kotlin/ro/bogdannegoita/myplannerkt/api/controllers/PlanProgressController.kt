package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ro.bogdannegoita.myplannerkt.api.requests.PlanProgressRequest
import ro.bogdannegoita.myplannerkt.api.requests.StepProgressRequest
import ro.bogdannegoita.myplannerkt.api.responses.PlanProgressResponse
import ro.bogdannegoita.myplannerkt.api.responses.StepProgressResponse
import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
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
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
    ): PlanProgressResponse? {
        return user(principal).getAcquiredPlan(id)?.let { PlanProgressResponse(it) }
    }

    @PutMapping("/{id}")
    fun update(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @RequestBody request: PlanProgressRequest,
    ): PlanProgressResponse? {
        val planProgress = user(principal).getAcquiredPlan(id) ?: return null
        planProgress.update(request.comment)
        return PlanProgressResponse(planProgress)
    }

    @PutMapping("/{id}/steps/{stepId}")
    fun updateStepProgress(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @PathVariable stepId: UUID,
        @RequestBody request: StepProgressRequest,
    ): StepProgressResponse? {
        val data = StepProgressDto(stepId, request.completed, request.comment)
        val stepProgress = user(principal).getAcquiredPlan(id)?.updateStepProgress(stepId, data)
        return stepProgress?.let { StepProgressResponse(it) }
    }

    @PutMapping("/{id}/sync")
    fun sync(@AuthenticationPrincipal principal: UserDetails, @PathVariable id: UUID): PlanProgressResponse? {
        val planProgress = user(principal).getAcquiredPlan(id)
            ?: return null
        planProgress.sync()
        return PlanProgressResponse(planProgress)
    }
}
