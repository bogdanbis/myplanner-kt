<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>Create a Plan</h2>
	<span class="page-subtitle">Create a Plan for yourself or to share with others.</span>
	<MpCard>
		<MpForm @submit="createPlan">
			<MpFormInput
				v-model="title"
				placeholder="The Title"
				id="title"
				class="large-input"
				description="Be concise. We recommend no more than 10 words."
			/>
			<MpFormTextarea
				id="short-description"
				label="Short Description"
				v-model="shortDescription"
				placeholder="A few key concepts to give an idea as to what this is about. Make it short!"
				show-count
			/>
			<MpFormTextarea
				id="description"
				label="Description"
				v-model="description"
				placeholder="Describe your plan in detail. How will it help other people?"
				show-count
			/>
			<MpFormSelect
				id="public"
				:options="[{value: false, label: 'Private'}, {value: true, label: 'Public'}]"
				label="Visibility"
				v-model="isPublic"
			/>
			<MpFormInput
				id="color"
				type="color"
				label="Primary color"
				description="Pick a color to personalize your plan. Make it recognizable at a glance."
				v-model="color"
			/>

			<template #actions>
				<MpButton type="submit">
					Submit
				</MpButton>
			</template>
		</MpForm>
	</MpCard>
</template>

<script setup>
import { ref } from 'vue';
import api from '@/api';
import { useRouter } from "vue-router";
import { useAuthStore } from '../../store/auth.js';

const authStore = useAuthStore();
const router = useRouter();

const title = ref('');
const description = ref('');
const shortDescription = ref('');
const isPublic = ref(false);
const color = ref('#5856D6');

const createPlan = async () => {
	await api.post('/plans/create', {
		title: title.value,
		description: description.value,
		shortDescription: shortDescription.value,
		isPublic: isPublic.value,
		color: color.value,
	});
	authStore.user.fetchCreatedPlans();
	router.push('/creator');
}
</script>
