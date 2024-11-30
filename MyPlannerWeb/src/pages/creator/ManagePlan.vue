<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle">View stats about your plan or manage your plan details.</span>

	<div class="halved">
		<MpCard title="Stats">
			<MpInlineValue label="Participants" :value="plan.stats.numberOfParticipants" />
			<MpInlineValue :value="plan.stats.completedStepsCount">
				<template #label>
					<span title="There can be a slight difference if not all of the participants are synced with the latest version.">
						Steps completed
						<MpIcon icon="info-circle" class="text-primary m-left-xs" />
					</span>
				</template>
			</MpInlineValue>
			<div class="m-top-l">
				<MpLink :to="`/creator/manage/${plan.id}/participants`">View participants</MpLink>
			</div>
		</MpCard>
		<MpCard title="Details">
			<MpInlineValue label="Last modified" :value="$date(plan.lastModifiedAt)" />
			<MpInlineValue label="Created" :value="$date(plan.createdAt)" />
			<MpInlineValue label="Number of steps" :value="plan.steps.length" />

			<template #actions>
				<MpLink :to="`/creator/manage/${plan.id}/edit`">Edit</MpLink>
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
	const planResponse = await api.get('/plans/created/' + planId);
	if (!planResponse)
		return router.push('/creator');
	plan.value = new Plan(planResponse);
})
</script>
