package ro.bogdannegoita.myplannerkt.persistence.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ro.bogdannegoita.myplannerkt.persistence.entities.TaskEntity
import java.util.*

interface TaskRepository : JpaRepository<TaskEntity, UUID>
