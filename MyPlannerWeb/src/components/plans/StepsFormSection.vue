<template>
	<MpFormSection
		v-if="steps.length > 0"
		:title="isRoot ? 'Steps' : 'Sub steps'"
		:smaller-title="!isRoot"
		:start-collapsed="stepsContainer.id"
		collapsible
		v-auto-animate
	>
		<MpCol
			v-for="(step, index) in steps"
			:key="step"
			class="mp-form-subsection"
			:class="{ 'mp-form-root-section': isRoot }"
		>
			<div class="change-index-container">
				<MpButton v-if="index < (steps.length - 1)" link icon="arrow-down" @click="moveStepDown(step)" />
				<MpButton v-if="index > 0" link icon="arrow-up" @click="moveStepUp(step)" />
				<span class="hover-info">Move item</span>
			</div>
			<MpFormInput
				:id="'step-title-' + (step.id || index)"
				label="Title"
				v-model="step.title"
			/>
			<MpFormTextarea
				:id="'step-description-' + step.id"
				label="Description"
				v-model="step.description"
			/>

			<StepsFormSection :steps-container="step" />

			<div class="mp-form-actions">
				<MpButton link @click="removeStep(step)" icon="dash" class="ms-auto">Remove</MpButton>
			</div>
		</MpCol>

		<MpCol cols="1">
			<MpButton @click="addNewStep" icon="plus-circle" :link="!isRoot" :class="{ 'm-left-xs': !isRoot }">
				{{ isRoot ? 'Step' : 'Sub step' }}
			</MpButton>
		</MpCol>
	</MpFormSection>
	<MpCol v-else cols="1">
		<MpButton @click="addNewStep" icon="plus-circle" :link="!isRoot" :class="{ 'm-left-xs': !isRoot }">
			{{ isRoot ? 'Step' : 'Sub step' }}
		</MpButton>
	</MpCol>
</template>

<script setup>
import Step from '@/models/Step.js';
import { sortBy as _sortBy } from 'lodash';
import { computed } from 'vue';

const { stepsContainer } = defineProps({
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
		document.getElementById('step-title-' + (stepsContainer.steps.length - 1))
				.scrollIntoView({ behavior: 'smooth' });
	}, 250);
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
