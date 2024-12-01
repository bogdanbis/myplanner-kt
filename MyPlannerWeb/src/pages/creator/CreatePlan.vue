<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>Create a Plan</h2>
	<span class="page-subtitle">Create a Plan for yourself or to share with others.</span>
	<MpCard>
		<PlanForm :plan="plan" @submit="createPlan">
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
	await api.post('/plans/create', plan.value);
	authStore.user.fetchCreatedPlans();
	router.push('/creator');
}
</script>
