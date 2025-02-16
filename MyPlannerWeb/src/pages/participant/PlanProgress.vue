<template>
	<MpBackLink to="/my-plans" route-name="My Plans" />
	<h2>{{ plan.title }}</h2>
	<h5 class="page-subtitle">
		<MpIcon icon="person-fill" />
		{{ plan.author.name }}
	</h5>
	<p class="fw-600">{{ plan.shortDescription }}</p>

	<div v-if="planProgress.updateAvailable" class="mb-xl">
		<p class="text-primary fw-600">
			{{ plan.author.name }} has made some changes. Sync to use the latest version.
		</p>
		<MpButton @click="syncWithPlan">Sync</MpButton>
	</div>

	<MpCard :style="primaryColor">
		<PlanImages
			:plan="plan"
			class="mb-m"
		/>
		<MpMultilineText :text="plan.description" />
		<MpForm @submit="saveComment" class="mt-m">
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
import PlanImages from '@/components/plans/PlanImages.vue';
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

const primaryColor = computed(() => ({ '--primary': `${plan.value.color}` }))

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
