package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.PlanProgress

class ParticipantResponse(planProgress: PlanProgress) {
    val progressId = planProgress.id
    val participant = ApplicationUserResponse(planProgress.user.value)
}
