<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>Create a Plan</h2>
	<span class="page-subtitle">Create a Plan for yourself or to share with others.</span>
	<MpCard>
		<PlanForm
			:plan="plan"
			@submit="createPlan"
			@upload-image="uploadImage"
			@delete-image="deleteImage"
		>
			<template #actions>
				<MpButton type="submit" :disabled="!hasRequiredFields" :busy="loading">
					Save
				</MpButton>
			</template>
		</PlanForm>
	</MpCard>
</template>

<script setup>
import api from '@/api';
import PlanForm from '@/components/plans/PlanForm.vue';
import Plan from '@/models/Plan.js';
import { useAuthStore } from '@/store/auth.js';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const authStore = useAuthStore();
const router = useRouter();

const plan = ref(new Plan());
const image = ref(null);

onMounted(() => {
	document.getElementById('title').focus()
})

const hasRequiredFields = computed(() => {
	return plan.value.title && plan.value.description && plan.value.shortDescription
			&& plan.value.isPublic != null && plan.value.color;
})

const loading = ref(false);
const createPlan = async () => {
	loading.value = true;
	if (!hasRequiredFields.value)
		return;
	const response = await api.post('/plans/create', plan.value);
	const createdPlan = new Plan(response);
	await createdPlan.uploadImage(image.value);
	authStore.user.fetchCreatedPlans();
	router.push('/creator');
}

const uploadImage = async (file) => {
	image.value = file;
	image.value.src = URL.createObjectURL(file);
	plan.value.images.push(image.value);
}

const deleteImage = async () => {
	plan.value.images = [];
	image.value = null;
}
</script>
