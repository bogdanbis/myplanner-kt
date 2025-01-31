package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ro.bogdannegoita.myplannerkt.api.requests.PlanRequest
import ro.bogdannegoita.myplannerkt.api.responses.*
import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.types.Photo
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

    @GetMapping("/search")
    fun getPublicPlans(@RequestParam title: String?): ResponseEntity<List<PlanSimpleResponse>> {
        if (title == null || title.length < 3)
            return ResponseEntity
                .of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    "Search term must be at least 3 characets long."))
                .build()
        return ResponseEntity.ok(myPlanner.findByTitle(title).map(::PlanSimpleResponse))
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

    @PostMapping("/created/{id}/images")
    fun uploadImage(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @RequestParam("photo") request: MultipartFile
    ): Photo? {
        val photo = Photo(id = null,
            name = request.originalFilename,
            contentType = request.contentType,
            content = request.bytes)
        return user(principal).getCreatedPlan(id)?.uploadImage(id, photo)
    }

    @DeleteMapping("/created/{id}/images/{imageId}")
    fun deleteImage(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @PathVariable imageId: UUID,
    ) {
        user(principal).getCreatedPlan(id)?.deleteImage(imageId)
    }

    @PostMapping("/created/{id}/steps/{stepId}/images")
    fun uploadImage(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @PathVariable stepId: UUID,
        @RequestParam("photo") request: MultipartFile
    ): Photo? {
        val photo = Photo(id = null,
            name = request.originalFilename,
            contentType = request.contentType,
            content = request.bytes)
        return user(principal).getCreatedPlan(id)
            ?.getStep(id)?.uploadImage(id, photo)
    }

    @GetMapping("/created/{id}/participants")
    fun getParticipants(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
    ): List<ParticipantProgressResponse>? {
        return user(principal).getCreatedPlan(id)?.getParticipants()
            ?.map(::ParticipantProgressResponse)
    }

    @GetMapping("/created/{id}/participants/{progressId}")
    fun getParticipantProgress(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @PathVariable progressId: UUID,
    ): ParticipantProgressResponse? {
        return user(principal).getCreatedPlan(id)?.getParticipantProgress(progressId)
            ?.let { ParticipantProgressResponse(it) }
    }

    @PostMapping("/created/{id}/invite")
    fun inviteUser(
        @AuthenticationPrincipal principal: UserDetails,
        @PathVariable id: UUID,
        @RequestParam email: String,
    ): PlanInviteResponse? {
        val invite = user(principal).inviteUser(id, email)
        return invite?.let { PlanInviteResponse(it) }
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
