<template>
	<MpBackLink to="/my-plans">My Plans</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle">{{ plan.shortDescription }}</span>

	<MpCard>
		<b class="text-secondary">About</b>
		<p><MpMultilineText :text="plan.description" /></p>

		<MpFormSection title="Steps">
			<div v-for="stepProgress in planProgress.steps" :key="stepProgress" class="mp-form-subsection">
				<span><b>{{ stepProgress.step.title }}</b></span>
				<p>{{ stepProgress.step.description }}</p>
				<MpFormCheckbox
					:id="'step-' + stepProgress.step.id + '-completed'"
					label="Completed"
					v-model="stepProgress.completed"
					@change="markAsCompleted(stepProgress.id)"
				/>
			</div>
		</MpFormSection>
	</MpCard>
</template>

<script setup>
import api from '@/api/index.js';
import PlanProgress from '@/models/PlanProgress.js';
import { useAuthStore } from '@/store/auth.js';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const planId = useRoute().params.id;

const authStore = useAuthStore();
const user = computed(() => authStore.user);
const planProgress = ref(new PlanProgress());

const plan = computed(() => planProgress.value.plan);

onMounted(async () => {
	const planResponse = await api.get('/plans/acquired/' + planId);
	if (!planResponse)
		router.push('/my-plans');
	planProgress.value = new PlanProgress(planResponse);
})

const markAsCompleted = async (stepId) => {
	await api.put('/plans/acquired/' + planProgress.value.id + '/steps/' + stepId, { completed: true });
}
</script>
