package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.Task

class TaskResponse(task: Task) {
    val id = task.id
    val title = task.title
    val description = task.description
    val index = task.index
}
