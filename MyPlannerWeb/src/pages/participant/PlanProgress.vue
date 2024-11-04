<template>
	<MpBackLink to="/my-plans">My Plans</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle">{{ plan.shortDescription }}</span>

	<div v-if="planProgress.updateAvailable" class="m-bottom-xxl">
		<p class="text-primary fw-600">
			{{ plan.author.name }} has made some changes. Sync to use the latest version.
		</p>
		<MpButton @click="syncWithPlan">Sync</MpButton>
	</div>

	<MpCard>
		<b class="text-secondary">About</b>
		<p><MpMultilineText :text="plan.description" /></p>
		<MpForm @submit="saveComment">
			<MpFormTextarea
				id="comment"
				placeholder="Notes or comments for yourself."
				v-model="comment"
			/>
			<template #actions>
				<MpButton v-if="comment !== planProgress.comment" type="submit" :busy="saving">
					Save
				</MpButton>
			</template>
		</MpForm>
		<StepProgressFormSection
			v-if="!loading"
			:steps-container="planProgress"
			:plan-progress-id="planProgress.id"
			is-root
			may-modify
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
const planId = useRoute().params.id;

const planProgress = ref(new PlanProgress());
const comment = ref('');
const loading = ref(true);
const saving = ref(false);

const plan = computed(() => planProgress.value.plan);

onMounted(async () => {
	const planResponse = await api.get('/plans/acquired/' + planId);
	if (!planResponse)
		return router.push('/my-plans');
	planProgress.value = new PlanProgress(planResponse);
	comment.value = planProgress.value.comment;
	loading.value = false;
})

const syncWithPlan = async () => {
	loading.value = true;
	if (comment.value !== planProgress.value.comment)
		await saveComment();
	const response = await api.put('/plans/acquired/' + planId + '/sync');
	planProgress.value = new PlanProgress(response);
	loading.value = false;
}

const saveComment = async () => {
	saving.value = true;
	const response = await api.put('/plans/acquired/' + planId, { comment: comment.value });
	planProgress.value.comment = response.comment;
	comment.value = response.comment;
	saving.value = false;
}
</script>
