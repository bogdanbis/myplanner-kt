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
		<div
			v-for="step in steps"
			:key="step"
			class="step"
		>
			<StepDetailsForm
				:step="step"
				:can-add-secondary-step="isRoot"
				@add-secondary-step="addSubStep(step)"
				@add-step="addNewStep(step)"
				@remove-step="removeStep(step)"
				@move-up="moveStepUp(step)"
				@move-down="moveStepDown(step)"
			/>
		</div>

		<MpCol v-if="isRoot" cols="1">
			<MpButton @click="addNewStep" icon="plus-circle" link>Step</MpButton>
		</MpCol>
	</MpFormSection>
</template>

<script setup>
import StepDetailsForm from '@/components/plans/StepDetailsForm.vue';
import Step from '@/models/Step.js';
import { sortBy as _sortBy } from 'lodash';
import { computed } from 'vue';

const { stepsContainer, isRoot } = defineProps({
	stepsContainer: {
		type: Object,
		required: true,
	},
	isRoot: {
		type: Boolean,
		required: false,
		default: false,
	},
});

const steps = computed(() => _sortBy(stepsContainer.steps, 'index'));

const addNewStep = () => {
	stepsContainer.steps.push(new Step({ index: stepsContainer.steps.length }));
	setTimeout(() => {
		document.getElementById('step-title-' + (stepsContainer.steps.length - 1)).focus();
	}, 250);
}

const addSubStep = (step) => {
	const theStep = stepsContainer.steps.find(it => it === step)
	theStep.steps.push(new Step({ index: theStep.steps.length }));
}

const removeStep = (step) => {
	if (!step) return;
	stepsContainer.steps.splice(stepsContainer.steps.indexOf(step), 1);
	steps.value.forEach((t, i) => t.index = i);
}

const moveStepUp = (step) => {
	if (step.index <= 0)
		return;
	const prev = steps.value[steps.value.indexOf(step) - 1];
	if (!prev) return;
	prev.index++;
	step.index--;
}

const moveStepDown = (step) => {
	if (step.index >= steps.value.length - 1)
		return;
	const next = steps.value[steps.value.indexOf(step) + 1];
	if (!next) return;
	next.index--;
	step.index++;
}
</script>
