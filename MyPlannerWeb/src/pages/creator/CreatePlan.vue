<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>Create a Plan</h2>
	<span class="page-subtitle">Create a Plan for yourself or to share with others.</span>
	<MpCard>
		<PlanForm :plan="plan" @submit="createPlan">
			<template #actions>
				<MpButton type="submit">
					Submit
				</MpButton>
			</template>
		</PlanForm>
	</MpCard>
</template>

<script setup>
import api from '@/api';
import PlanForm from '@/components/plans/PlanForm.vue';
import { useAuthStore } from '@/store/auth.js';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const authStore = useAuthStore();
const router = useRouter();

const plan = ref({
	title: '',
	description: '',
	shortDescription: '',
	isPublic: false,
	color: '#5856D6',
});

const createPlan = async () => {
	await api.post('/plans/create', {
		title: plan.value.title,
		description: plan.value.description,
		shortDescription: plan.value.shortDescription,
		isPublic: plan.value.isPublic,
		color: plan.value.color,
	});
	authStore.user.fetchCreatedPlans();
	router.push('/creator');
}
</script>
