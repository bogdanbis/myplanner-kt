<template>
	<div class="d-flex align-start">
		<MpButton
			v-if="step.steps.length > 0"
			@click="toggleCollapsed"
			:class="step.steps.length > 0 ? '' : 'd-hidden'"
			class="step-progress-collapse-button"
			:icon="collapsed ? 'chevron-right' : 'chevron-down'"
			link
		/>
		<div class="step" v-auto-animate>
			<MpCheckbox
				v-if="step.steps.length === 0"
				:id="'step-' + step.id + '-completed'"
				:label="step.title"
				v-model="completed"
				class="step-title"
				strikethrough
			/>
			<div v-else @click="toggleCollapsed" class="step-title d-flex cursor-pointer">
				{{ step.title }}
			</div>
			<MpMultilineText
				:text="step.description"
				class="step-description"
			/>
			<div v-if="!collapsed && step.steps.length > 0">
				<StepPublicInfo
					v-for="secondaryStep in step.steps"
					:step="secondaryStep"
					class="step-form-secondary"
				/>
			</div>
		</div>
	</div>
</template>

<script setup>
import { useCollapse } from '@/composables/collapse.js';
import Step from '@/models/Step.js';
import { ref } from 'vue';

const { step } = defineProps({
	step: {
		type: Step,
		required: true,
	},
});

const completed = ref(false);

const { collapsed, toggleCollapsed } = useCollapse(true, false)
</script>
