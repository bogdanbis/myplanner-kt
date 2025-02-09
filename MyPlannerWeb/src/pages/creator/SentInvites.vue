<template>
	<MpBackLink :to="`/creator/manage/${planId}`">Manage</MpBackLink>
	<h2>{{ plan.title }}</h2>

	<div class="mb-m">
		<MpLink :to="`/creator/manage/${planId}/participants`">
			View participants
		</MpLink>
		<InviteParticipantButton :plan="plan" class="w-50-desktop" />
	</div>
	<MpCard title="Sent invites" :style="{ '--primary': plan.color }">
		<MpTable :fields="tableFields">
			<tr v-for="invite in sentInvites">
				<td>{{ invite.recipient.name }}</td>
				<td>{{ statuses[invite.status] }}</td>
				<td>{{ $relativeDate(invite.respondedAt) || '-' }}</td>
				<td>{{ $relativeDate(invite.createdAt) }}</td>
			</tr>
		</MpTable>
	</MpCard>
</template>

<script setup>
import api from '@/api/index.js';
import InviteParticipantButton from '@/components/plans/manage-plan/InviteParticipantButton.vue';
import Plan from '@/models/Plan.js';
import PlanInvite from '@/models/PlanInvite.js';
import InviteStatus from '@/models/types/InviteStatus.js';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const tableFields = [
	{ label: 'Sent to' },
	{ label: 'Status' },
	{ label: 'Responded at' },
	{ label: 'Sent at' },
]

const statuses = {
	[InviteStatus.PENDING]: 'Pending',
	[InviteStatus.DECLINED]: 'Declined',
	[InviteStatus.ACCEPTED]: 'Accepted',
	[InviteStatus.REVOKED]: 'Revoked',
}

const router = useRouter();
const route = useRoute();
const planId = route.params.id;
const plan = ref(new Plan());
const sentInvites = ref([]);

onMounted(() => {
	fetchPlan();
	fetchInvites();
})

const fetchPlan = async () => {
	const response = await api.get(`/plans/${planId}`);
	if (!response)
		return router.push('/creator');
	plan.value = new Plan(response);
}

const fetchInvites = async () => {
	const response = await api.get('/invites/sent');
	sentInvites.value = response.map(it => new PlanInvite(it));
}
</script>
