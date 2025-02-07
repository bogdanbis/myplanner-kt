<template>
	<MpFormSection
		:title="isRoot ? 'Steps' : ''"
		:smaller-title="!isRoot"
		:start-collapsed="stepsContainer.id"
		class="steps-form"
		:class="{ 'step-form-secondary': !isRoot }"
		:collapsible="!isRoot"
		v-auto-animate
	>
		<div v-if="isRoot" class="steps-progress">
			<div>Completed {{ stepsContainer.completedStepsCount }}/{{ stepsContainer.totalStepsCount }}</div>
			<ProgressCircle
				v-if="stepsContainer.completedStepsCount"
				:value="stepsContainer.completedStepsCount"
				:max="stepsContainer.totalStepsCount"
				class="ml-xs"
			/>
		</div>
		<div
			v-for="stepProgress in stepsContainer.steps"
			:key="stepProgress"
		>
			<StepProgressDetails
				:steps-container="stepProgress"
				:plan-progress-id="planProgressId"
				:may-modify
			/>
		</div>
	</MpFormSection>
</template>

<script setup>
import StepProgressDetails from '@/components/plan-progress/StepProgressDetails.vue';
import PlanProgress from '@/models/PlanProgress.js';
import StepProgress from '@/models/StepProgress.js';

const { planProgressId, stepsContainer } = defineProps({
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
</script>
