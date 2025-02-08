package ro.bogdannegoita.myplannerkt.persistence.daos

import ro.bogdannegoita.myplannerkt.commons.dtos.StepDto
import java.util.*

interface StepContainerDao {
    fun addStep(containerId: UUID, data: StepDto): StepDto
    fun removeStep(id: UUID)
}
