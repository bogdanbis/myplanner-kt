<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>Edit Plan</h2>
	<span class="page-subtitle">Make changes to your Plan. You can make these anytime you want.</span>
	<MpCard>
		<MpForm @submit="updatePlan">
			<MpFormInput
				v-model="planEdits.title"
				placeholder="The Title"
				id="title"
				class="large-input"
				description="Be concise. We recommend no more than 10 words."
			/>
			<MpFormTextarea
				id="short-description"
				label="Short Description"
				v-model="planEdits.shortDescription"
				placeholder="A few key concepts to give an idea as to what this is about. Make it short!"
				show-count
			/>
			<MpFormTextarea
				id="description"
				label="Description"
				v-model="planEdits.description"
				placeholder="Describe your plan in detail. How will it help other people?"
				show-count
			/>
			<MpFormSelect
				id="public"
				:options="[{value: false, label: 'Private'}, {value: true, label: 'Public'}]"
				label="Visibility"
				v-model="planEdits.isPublic"
			/>
			<MpFormInput
				id="color"
				type="color"
				label="Primary color"
				description="Pick a color to personalize your plan. Make it recognizable at a glance."
				v-model="planEdits.color"
			/>

			<template #actions>
				<MpButton v-show="hasChanges" @click="cancelChanges" class="secondary" :disabled="!hasChanges">
					Cancel
				</MpButton>
				<MpButton type="submit" :disabled="!hasChanges">
					Save
				</MpButton>
			</template>
		</MpForm>
	</MpCard>
</template>

<script setup>
import api from '@/api';
import { useAuthStore } from '@/store/auth.js';
import { isEqual } from 'lodash';
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const planId = useRoute().params.id;

const authStore = useAuthStore();
const user = computed(() => authStore.user);
const plan = ref({});
const planEdits = ref({
	title: '',
	description: '',
	shortDescription: '',
	isPublic: '',
	color: '#ffffff',
});

onMounted(async () => {
	if (user.value?.createdPlans == null)
		await user.value?.fetchCreatedPlans();
	plan.value = user.value.createdPlans.find(p => p.id === planId);
	planEdits.value = { ...plan.value };
})

const hasChanges = computed(() => !isEqual(plan.value, planEdits.value))

const updatePlan = async () => {
	if (!hasChanges.value) return
	plan.value = await api.put('/plans/' + planId, planEdits.value);
}

const cancelChanges = () => {
	planEdits.value = { ...plan.value };
}
</script>
