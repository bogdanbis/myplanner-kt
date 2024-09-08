package ro.bogdannegoita.myplannerkt.api.requests

import ro.bogdannegoita.myplannerkt.commons.TaskDto

class PlanRequest(
    val title: String,
    val shortDescription: String,
    val description: String,
    val color: String,
    val isPublic: Boolean,
    val tasks: List<TaskDto>? = null,
)
