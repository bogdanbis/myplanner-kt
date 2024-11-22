<template>
	<MpBackLink :to="`/creator/manage/${planId}/participants`">Participants</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle"><b>{{ participant.name }}</b>'s progress</span>

	<MpCard>
		<b class="text-secondary">About</b>
		<p>
			<MpMultilineText :text="plan.description" />
		</p>
		<StepProgressFormSection
			v-if="!loading"
			:may-modify="false"
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
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const route = useRoute()
const planId = route.params.id;
const planProgressId = route.params.progressId

const planProgress = ref(new PlanProgress());
const loading = ref(true);

const plan = computed(() => planProgress.value.plan);
const participant = computed(() => planProgress.value.participant);

onMounted(async () => {
	const [planResponse, planProgressResponse] = await Promise.all([
		api.get(`/plans/created/${planId}`),
		api.get(`/plans/created/${planId}/participant-progress/${planProgressId}`),
	]);
	if (!planResponse)
		return router.push('/my-plans');
	planProgress.value = new PlanProgress({ ...planProgressResponse, plan: planResponse });
	loading.value = false;
})
</script>
