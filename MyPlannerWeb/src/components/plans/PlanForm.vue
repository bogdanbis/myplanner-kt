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
</script>
