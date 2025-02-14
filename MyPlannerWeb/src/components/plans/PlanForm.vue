<template>
	<MpForm class="tall">
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
			description="Pick a color to personalize your plan and make it easier to recognize. Make sure it is visible in both light and dark mode."
			v-model="plan.color"
		/>

		<PlanImages
			:plan="plan"
			upload-enabled
			@upload="$emit('upload-image', $event)"
			@delete="$emit('delete-image', $event)"
			class="mt-m"
		/>

		<StepsFormSection
			v-if="!loading"
			:steps-container="plan"
			is-root
		/>

		<slot></slot>
		<template #actions>
			<slot name="actions"></slot>
		</template>
	</MpForm>
</template>

<script setup>
import PlanImages from '@/components/plans/PlanImages.vue';
import Plan from '@/models/Plan.js';
import StepsFormSection from './StepsFormSection.vue';

defineProps({
	plan: {
		type: Plan,
		required: true,
	},
	loading: {
		type: Boolean,
		required: false,
		default: false,
	},
})

defineEmits(['submit', 'upload-image', 'delete-image']);
</script>
