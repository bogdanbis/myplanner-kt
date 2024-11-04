<template>
	<MpFormSection
		:title="isRoot ? 'Steps' : 'Sub steps'"
		:smaller-title="!isRoot"
		:start-collapsed="stepsContainer.id"
		collapsible
		v-auto-animate
	>
		<div
			v-for="stepProgress in stepsContainer.steps"
			:key="stepProgress"
			class="mp-form-subsection"
			:class="{ 'mp-form-root-section': isRoot }"
		>
			<MpFormCheckbox
				v-if="stepProgress.steps.length === 0"
				:disabled="!mayModify"
				:id="'step-' + stepProgress.step.id + '-completed'"
				:label="stepProgress.step.title"
				v-model="stepProgress.completed"
				@change="markAsCompleted(stepProgress)"
				class="fw-700"
			/>
			<span v-else><b>{{ stepProgress.step.title }}</b></span>
			<p class="text-secondary">{{ stepProgress.step.description }}</p>

			<StepProgressFormSection
				v-if="stepProgress.steps.length > 0"
				:steps-container="stepProgress"
				:plan-progress-id
				:may-modify
			/>
		</div>
	</MpFormSection>
</template>

<script setup>
import api from '@/api/index.js';
import PlanProgress from '@/models/PlanProgress.js';
import StepProgress from '@/models/StepProgress.js';

const props = defineProps({
	mayModify: {
		type: Boolean,
		required: true,
	},
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

const markAsCompleted = async (stepProgress) => {
	await api.put('/plans/acquired/' + props.planProgressId + '/steps/' + stepProgress.id,
			{ completed: stepProgress.completed });
}
</script>
