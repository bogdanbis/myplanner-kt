<template>
	<MpBackLink to="/my-plans">My Plans</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle">{{ plan.shortDescription }}</span>

	<MpCard>
		<b class="text-secondary">About</b>
		<p>{{ plan.description }}</p>

		<MpFormSection title="Tasks">
			<div v-for="taskProgress in planProgress.tasks" :key="taskProgress" class="mp-form-subsection">
				<span><b>{{ taskProgress.task.title }}</b></span>
				<p>{{ taskProgress.task.description }}</p>
				<MpFormCheckbox
					:id="'task-' + taskProgress.task.id + '-completed'"
					label="Completed"
					v-model="taskProgress.completed"
					@change="markAsCompleted(taskProgress.id)"
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

const markAsCompleted = async (taskId) => {
	await api.put('/plans/acquired/' + planProgress.value.id + '/tasks/' + taskId, { completed: true });
}
</script>
