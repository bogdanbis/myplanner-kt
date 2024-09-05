package ro.bogdannegoita.myplannerkt.domain

import ro.bogdannegoita.myplannerkt.commons.TaskDto
import ro.bogdannegoita.myplannerkt.commons.TaskProgressDto
import ro.bogdannegoita.myplannerkt.persistence.daos.TaskProgressDao
import java.util.*

class TaskProgress(data: TaskProgressDto, task: Task, private val dao: TaskProgressDao) {
    val id: UUID = data.id!!
    val completed by data::completed
    val taskData: TaskDto = data.task
}
