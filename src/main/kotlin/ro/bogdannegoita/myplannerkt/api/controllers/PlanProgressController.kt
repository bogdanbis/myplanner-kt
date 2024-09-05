package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ro.bogdannegoita.myplannerkt.api.requests.TaskProgressRequest
import ro.bogdannegoita.myplannerkt.api.responses.PlanProgressResponse
import ro.bogdannegoita.myplannerkt.api.responses.TaskProgressResponse
import ro.bogdannegoita.myplannerkt.commons.TaskProgressDto
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

    @PutMapping("/{id}/tasks/{taskId}")
    fun updateTaskProgress(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @PathVariable taskId: UUID,
        @RequestBody request: TaskProgressRequest,
    ): TaskProgressResponse? {
        val data = TaskProgressDto(taskId, request.completed, null)
        val taskProgress = user(principal).getAcquiredPlan(id)?.updateTaskProgress(taskId, data)
        return taskProgress?.let { TaskProgressResponse(it) }
    }
}
