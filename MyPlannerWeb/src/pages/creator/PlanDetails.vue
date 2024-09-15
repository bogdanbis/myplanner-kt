<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle">View stats about your plan & manage your plan's data.</span>

	<div class="halved">
		<MpCard title="Stats">
			<span>Participants in total: <b>{{ plan.stats.numberOfParticipants }}</b></span>
		</MpCard>
		<MpCard title="Details">
			<p>Last modified: <b>{{ $date(plan.lastModifiedAt) }}</b></p>
			<p>Created: {{ $date(plan.createdAt) }}</p>
			<p>Tasks: <b>{{ plan.tasks.length }}</b></p>
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
