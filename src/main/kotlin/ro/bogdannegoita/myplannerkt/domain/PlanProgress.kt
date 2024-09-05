package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.PlanDto
import ro.bogdannegoita.myplannerkt.commons.PlanProgressDto

class PlanProgress(
    data: PlanProgressDto, plan: Plan,
) : Comparable<PlanProgress> {
    val id = data.id!!
    val planData: PlanDto = plan.data
    val acquiredAt by data::acquiredAt

    override fun compareTo(other: PlanProgress) = other.acquiredAt.compareTo(acquiredAt)
}
