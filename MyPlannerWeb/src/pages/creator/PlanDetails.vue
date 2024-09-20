<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle">View stats about your plan or manage your plan details.</span>

	<div class="halved">
		<MpCard title="Stats">
			<MpInlineValue label="Participants" :value="plan.stats.numberOfParticipants" />
			<MpInlineValue label="Steps completed by others" :value="plan.stats.completedStepsCount" />
		</MpCard>
		<MpCard title="Details">
			<MpInlineValue label="Last modified" :value="$date(plan.lastModifiedAt)" />
			<MpInlineValue label="Created" :value="$date(plan.createdAt)" />
			<MpInlineValue label="Number of steps" :value="plan.steps.length" />

			<template #actions>
				<MpLink :to="'/creator/details/' + plan.id + '/edit'">Edit</MpLink>
			</template>
		</MpCard>
	</div>
</template>

<script setup>
import api from '@/api/index.js';
import Plan from '@/models/Plan.js';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const planId = useRoute().params.id;

const plan = ref(new Plan());

onMounted(async () => {
	const planResponse = await api.get('/plans/' + planId);
	if (!planResponse)
		router.push('/creator');
	plan.value = new Plan(planResponse);
})
</script>
