<template>
	<MpBackLink to="/my-plans">My Plans</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle">{{ plan.shortDescription }}</span>

	<div v-if="planProgress.updateAvailable" class="m-bottom-xxl">
		<p class="text-primary fw-600">{{ plan.author.fullName }} has made some changes. Sync to use the latest version.</p>
		<MpButton @click="syncWithPlan">Sync</MpButton>
	</div>

	<MpCard>
		<b class="text-secondary">About</b>
		<p><MpMultilineText :text="plan.description" /></p>
		<StepProgressFormSection
			:steps-container="planProgress"
			:plan-progress-id="planProgress.id"
			is-root
		/>
	</MpCard>
</template>

<script setup>
import api from '@/api/index.js';
import StepProgressFormSection from '@/components/plan-progress/StepProgressFormSection.vue';
import PlanProgress from '@/models/PlanProgress.js';
import { useAuthStore } from '@/store/auth.js';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const planId = useRoute().params.id;

const planProgress = ref(new PlanProgress());

const plan = computed(() => planProgress.value.plan);

onMounted(async () => {
	const planResponse = await api.get('/plans/acquired/' + planId);
	if (!planResponse)
		router.push('/my-plans');
	else
		planProgress.value = new PlanProgress(planResponse);
})

const syncWithPlan = async () => {
	const response = await api.put('/plans/acquired/' + planId + '/sync');
	planProgress.value = new PlanProgress(response);
}
</script>
