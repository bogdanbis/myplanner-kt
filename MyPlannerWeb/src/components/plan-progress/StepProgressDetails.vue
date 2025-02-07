<template>
	<div class="d-flex align-start">
		<MpButton
			v-if="stepsContainer.steps.length > 0"
			@click="toggleCollapsed"
			:class="stepsContainer.steps.length > 0 ? '' : 'd-hidden'"
			class="step-progress-collapse-button"
			:icon="collapsed ? 'chevron-right' : 'chevron-down'"
			link
		/>
		<div class="step">
			<MpCheckbox
				v-if="stepsContainer.steps.length === 0"
				:disabled="!mayModify"
				:id="'step-' + stepsContainer.step.id + '-completed'"
				:label="stepsContainer.step.title"
				v-model="stepsContainer.completed"
				@change="markAsCompleted"
				class="step-title"
			/>
			<div v-else class="step-title d-flex">
				{{ stepsContainer.step.title }}
				<ProgressCircle
					v-if="stepsContainer.completedStepsCount"
					:value="stepsContainer.completedStepsCount"
					:max="stepsContainer.totalStepsCount"
					class="ml-xs"
				/>
			</div>
			<MpMultilineText
				:text="stepsContainer.step.description"
				class="step-description"
			/>
			<div v-auto-animate>
				<StepProgressFormSection
					v-if="!collapsed && stepsContainer.steps.length > 0"
					:steps-container="stepsContainer"
					:plan-progress-id="planProgressId"
					:may-modify
				/>
			</div>
		</div>
	</div>
</template>

<script setup>
import api from '@/api/index.js';
import StepProgressFormSection from '@/components/plan-progress/StepProgressFormSection.vue';
import { useCollapse } from '@/composables/collapse.js';
import PlanProgress from '@/models/PlanProgress.js';
import StepProgress from '@/models/StepProgress.js';

const { stepsContainer, planProgressId } = defineProps({
	mayModify: {
		type: Boolean,
		required: true,
	},
	planProgressId: {
		type: String,
		required: true,
	},
	stepsContainer: {
		type: [PlanProgress, StepProgress],
		required: true,
	},
})

const markAsCompleted = async () => {
	await api.put('/plans/acquired/' + planProgressId + '/steps/' + stepsContainer.id,
			{ completed: stepsContainer.completed });
}

const { collapsed, toggleCollapsed } = useCollapse(true, false)
</script>
