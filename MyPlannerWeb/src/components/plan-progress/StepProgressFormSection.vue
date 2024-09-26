<template>
	<MpFormSection
		:title="isRoot ? 'Steps' : 'Sub steps'"
		:smaller-title="!isRoot"
		:start-collapsed="stepsContainer.id"
		collapsible
		v-auto-animate
	>
		<div v-for="stepProgress in stepsContainer.steps" :key="stepProgress" class="mp-form-subsection">
			<span><b>{{ stepProgress.step.title }}</b></span>
			<p>{{ stepProgress.step.description }}</p>

			<StepProgressFormSection
				v-if="stepProgress.steps.length > 0"
				:steps-container="stepProgress"
				:plan-progress-id
			/>
			<MpFormCheckbox
				v-else
				:id="'step-' + stepProgress.step.id + '-completed'"
				label="Completed"
				v-model="stepProgress.completed"
				@change="markAsCompleted(stepProgress.id)"
			/>
		</div>
	</MpFormSection>
</template>

<script setup>
import api from '@/api/index.js';
import PlanProgress from '@/models/PlanProgress.js';
import StepProgress from '@/models/StepProgress.js';

const props = defineProps({
	isRoot: {
		type: Boolean,
		required: false,
		default: false,
	},
	planProgressId: {
		type: String,
		required: false,
	},
	stepsContainer: {
		type: [PlanProgress, StepProgress],
		required: true,
	},
});

const markAsCompleted = async (stepId) => {
	await api.put('/plans/acquired/' + props.planProgressId + '/steps/' + stepId, { completed: true });
}
</script>
