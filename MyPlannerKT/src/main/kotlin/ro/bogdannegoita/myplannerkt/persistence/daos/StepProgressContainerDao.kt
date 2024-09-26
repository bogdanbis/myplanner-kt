package ro.bogdannegoita.myplannerkt.persistence.daos

import ro.bogdannegoita.myplannerkt.commons.StepProgressDto
import java.util.*

interface StepProgressContainerDao {
    fun addStepProgress(stepContainerId: UUID, stepId: UUID): StepProgressDto
    fun removeStepProgress(id: UUID)
}
