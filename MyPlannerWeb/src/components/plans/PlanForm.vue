<template>
	<MpForm @submit="emit('submit')">
		<MpFormInput
			v-model="plan.title"
			placeholder="The Title"
			id="title"
			class="large-input"
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

		<MpFormSection title="Tasks" v-auto-animate>
			<MpCol v-for="(task, index) in tasks" :key="task" class="mp-form-subsection">
				<div class="change-index-container">
					<MpButton v-if="index < (tasks.length - 1)" link icon="arrow-down" @click="moveTaskDown(task)" />
					<MpButton v-if="index > 0" link icon="arrow-up" @click="moveTaskUp(task)" />
					<span class="hover-info">Move item</span>
				</div>
				<MpFormInput
					:id="'task-title-' + index"
					label="Title"
					v-model="task.title"
				/>
				<MpFormTextarea
					:id="'task-description-' + index"
					label="Description"
					v-model="task.description"
				/>
				<div class="mp-form-actions">
					<MpButton link @click="removeTask(task)" icon="dash" class="ms-auto">Remove</MpButton>
				</div>
			</MpCol>
		</MpFormSection>
		<MpCol cols="1">
			<MpButton @click="addNewTask" icon="plus-circle">Task</MpButton>
		</MpCol>

		<slot></slot>
		<template #actions>
			<slot name="actions"></slot>
		</template>
	</MpForm>
</template>

<script setup>

import Task from '@/models/Task.js';
import { sortBy } from 'lodash';
import { computed } from 'vue';

const { plan } = defineProps({
	plan: {
		type: Object,
		required: true,
	},
})

const emit = defineEmits(['submit']);

const tasks = computed(() => sortBy(plan.tasks, 'index'));

const addNewTask = () => {
	plan.tasks.push(new Task({ index: plan.tasks.length }));
	setTimeout(() => {
		document.getElementById('task-title-' + (plan.tasks.length - 1)).scrollIntoView({ behavior: 'smooth' });
	}, 250);
}

const removeTask = (task) => {
	plan.tasks.splice(plan.tasks.indexOf(task), 1);
	tasks.value.forEach((t, i) => t.index = i + 1);
}

const moveTaskUp = (task) => {
	if (task.index <= 0)
		return;
	const prevTask = tasks.value[tasks.value.indexOf(task) - 1];
	prevTask.index++;
	task.index--;
}

const moveTaskDown = (task) => {
	if (task.index >= tasks.value.length - 1)
		return;
	const nextTask = tasks.value[tasks.value.indexOf(task) + 1];
	nextTask.index--;
	task.index++;
}
</script>
