<template>
	<MpForm @submit="emit('submit')" class="tall">
		<MpFormInput
			v-model="plan.title"
			placeholder="The Title"
			id="title"
			large unlabeled
			description="Be concise. We recommend no more than 10 words."
		/>
		<MpFormTextarea
			id="short-description"
			label="Short Description"
			v-model="plan.shortDescription"
			placeholder="A few key concepts to give an idea as to what this is about."
			description="This is used in previews. It should be just a sentence or two."
		/>
		<MpFormTextarea
			id="description"
			label="Description"
			v-model="plan.description"
			placeholder="Describe your plan in detail. How will it help other people?"
		/>
		<MpFormSelect
			id="public"
			:options="[{value: false, label: 'Private'}, {value: true, label: 'Public'}]"
			label="Visibility"
			v-model="plan.isPublic"
		/>
		<MpFormInput
			id="color"
			type="color"
			label="Primary color"
			description="Pick a color to personalize your plan. Make it recognizable at a glance."
			v-model="plan.color"
		/>

		<MpFormSection title="Steps" v-auto-animate>
			<MpCol v-for="(step, index) in steps" :key="step" class="mp-form-subsection">
				<div class="change-index-container">
					<MpButton v-if="index < (steps.length - 1)" link icon="arrow-down" @click="moveStepDown(step)" />
					<MpButton v-if="index > 0" link icon="arrow-up" @click="moveStepUp(step)" />
					<span class="hover-info">Move item</span>
				</div>
				<MpFormInput
					:id="'step-title-' + index"
					label="Title"
					v-model="step.title"
				/>
				<MpFormTextarea
					:id="'step-description-' + index"
					label="Description"
					v-model="step.description"
				/>
				<div class="mp-form-actions">
					<MpButton link @click="removeStep(step)" icon="dash" class="ms-auto">Remove</MpButton>
				</div>
			</MpCol>
		</MpFormSection>
		<MpCol cols="1">
			<MpButton @click="addNewStep" icon="plus-circle">Step</MpButton>
		</MpCol>

		<slot></slot>
		<template #actions>
			<slot name="actions"></slot>
		</template>
	</MpForm>
</template>

<script setup>

import Step from '@/models/Step.js';
import { sortBy } from 'lodash';
import { computed } from 'vue';

const { plan } = defineProps({
	plan: {
		type: Object,
		required: true,
	},
})

const emit = defineEmits(['submit']);

const steps = computed(() => sortBy(plan.steps, 'index'));

const addNewStep = () => {
	plan.steps.push(new Step({ index: plan.steps.length }));
	setTimeout(() => {
		document.getElementById('step-title-' + (plan.steps.length - 1)).scrollIntoView({ behavior: 'smooth' });
	}, 250);
}

const removeStep = (step) => {
	plan.steps.splice(plan.steps.indexOf(step), 1);
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
