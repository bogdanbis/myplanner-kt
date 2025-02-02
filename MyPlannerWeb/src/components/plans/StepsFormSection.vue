<template>
	<MpFormSection
		:title="isRoot ? 'Steps' : ''"
		:smaller-title="!isRoot"
		:start-collapsed="stepsContainer.id"
		class="steps-form mb-xxl"
		:class="{ 'step-form-secondary': !isRoot }"
		:collapsible="!isRoot"
		v-auto-animate
	>
		<div
			v-for="step in steps"
			:key="step"
			class="step"
		>
			<MpFormInput
				:id="'step-title-' + step.index"
				placeholder="Step title"
				unlabeled
				v-model="step.title"
				class="step-title"
				@focus="onFocusStep(step)"
				@focusout="onFocusOut(step)"
			/>
			<MpFormTextarea
				:id="'step-description-' + step.index"
				placeholder="Description"
				unlabeled
				v-model="step.description"
				class="step-description"
				@focus="onFocusStep(step)"
				@focusout="onFocusOut(step)"
			/>
			<div v-if="focused === step || focusedStep === step" class="step-actions">
				<MpButton v-if="isRoot" link @click="addSubStep(step)" icon="plus-lg">Step</MpButton>
				<MpButton link @click="removeStep(step)" icon="dash" class="danger">Remove</MpButton>
			</div>

			<StepsFormSection
				v-if="step.steps.length > 0"
				:steps-container="step"
				:focused-step="focused"
				@step-focused="onFocusStep"
				class="ml-xxl"
			/>
		</div>

		<MpCol v-if="isRoot" cols="1">
			<MpButton @click="addNewStep" icon="plus-circle" link>Step</MpButton>
		</MpCol>
	</MpFormSection>
</template>

<script setup>
import Step from '@/models/Step.js';
import { sortBy as _sortBy } from 'lodash';
import { computed, ref } from 'vue';

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
	focusedStep: {
		type: Object,
		required: false,
	},
});

const emit = defineEmits(['step-focused']);

const focused = ref(null);

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

const onFocusStep = (step) => {
	if (isRoot) {
		focused.value = step;
	} else {
		emit('step-focused', step);
	}
}

const onFocusOut = (step) => {
	removeStepIfEmpty(step)
}

const removeStepIfEmpty = (step) => {
	if (!step.title && !step.description)
		removeStep(step);
}

const removeStep = (step) => {
	stepsContainer.steps.splice(stepsContainer.steps.indexOf(step), 1);
	steps.value.forEach((t, i) => t.index = i);
}

const moveStepUp = (step) => {
	if (step.index <= 0)
		return;
	const prev = steps.value[steps.value.indexOf(step) - 1];
	prev.index++;
	step.index--;
}

const moveStepDown = (step) => {
	if (step.index >= steps.value.length - 1)
		return;
	const next = steps.value[steps.value.indexOf(step) + 1];
	next.index--;
	step.index++;
}
</script>
