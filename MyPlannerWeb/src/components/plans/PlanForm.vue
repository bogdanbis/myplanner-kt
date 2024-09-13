<template>
	<MpForm @submit="emit('submit')" v-auto-animate>
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

		<MpFormSectionTitle>Tasks</MpFormSectionTitle>
		<MpCol v-for="(task, index) in plan.tasks" :key="index" class="mp-form-subsection">
			<div class="change-index-container">
				<MpButton link icon="arrow-down" />
				<MpButton link icon="arrow-up" />
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
				<MpButton link @click="removeTask(task)" icon="dash">Remove</MpButton>
			</div>
		</MpCol>
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

const { plan } = defineProps({
	plan: {
		type: Object,
		required: true,
	},
})

const emit = defineEmits(['submit']);

const addNewTask = () => {
	plan.tasks.push({
		title: '',
		description: '',
	});
	setTimeout(() => {
		document.getElementById('task-title-' + (plan.tasks.length - 1)).scrollIntoView({ behavior: 'smooth' });
	}, 250);
}

const removeTask = (task) => {
	plan.tasks.splice(plan.tasks.indexOf(task), 1);
}
</script>
