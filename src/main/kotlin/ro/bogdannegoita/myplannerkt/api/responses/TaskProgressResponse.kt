package ro.bogdannegoita.myplannerkt.api.responses

import ro.bogdannegoita.myplannerkt.domain.TaskProgress

class TaskProgressResponse(taskProgress: TaskProgress) {
    val id = taskProgress.id
    val completed = taskProgress.completed
    val task = taskProgress.taskData
}
