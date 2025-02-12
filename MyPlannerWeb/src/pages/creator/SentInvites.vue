<template>
	<MpBackLink :to="`/creator/manage/${planId}`">Manage</MpBackLink>
	<h2>{{ plan.title }}</h2>

	<div class="mb-m" :style="{ '--primary': plan.color }">
		<MpLink :to="`/creator/manage/${planId}/participants`">
			View participants
		</MpLink>
		<InviteParticipantButton :plan="plan" class="w-50-desktop" />
	</div>
	<MpCard title="Sent invites">
		<MpTable :fields="tableFields" :busy="loading">
			<tr v-for="invite in sentInvites">
				<td class="line-height-normal">
					<span>{{ invite.recipient.name }}<br /></span>
					<span class="text-secondary font-size-s">{{ invite.recipient.email }}</span>
				</td>
				<td>{{ invite.plan.title }}</td>
				<td>
					<InviteStatus :status="invite.status" />
				</td>
				<td>{{ $relativeDate(invite.respondedAt) || '-' }}</td>
				<td>{{ $relativeDate(invite.createdAt) }}</td>
				<td>
					<MpLinkButtonWithConfirm
						v-if="invite.isPending"
						@confirm="revokeInvite(invite)"
						confirm-text="Revoke"
						:busy="loading"
					>
						Revoke
					</MpLinkButtonWithConfirm>
				</td>
			</tr>
		</MpTable>
	</MpCard>
</template>

<script setup>
import api from '@/api/index.js';
import InviteStatus from '@/components/plans/InviteStatus.vue';
import InviteParticipantButton from '@/components/plans/manage-plan/InviteParticipantButton.vue';
import Plan from '@/models/Plan.js';
import PlanInvite from '@/models/PlanInvite.js';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const tableFields = [
	{ label: 'Sent to' },
	{ label: 'Plan' },
	{ label: 'Status' },
	{ label: 'Responded at' },
	{ label: 'Sent at' },
]

const router = useRouter();
const route = useRoute();
const planId = route.params.id;
const plan = ref(new Plan());
const sentInvites = ref([]);
const loading = ref(false);

onMounted(async () => {
	loading.value = true;
	await fetchPlan();
	await fetchInvites();
	loading.value = false;
})

const fetchPlan = async () => {
	const response = await api.get(`/plans/${planId}`);
	if (!response)
		return router.push('/creator');
	plan.value = new Plan(response);
}

const fetchInvites = async () => {
	const response = await api.get('/invites/sent');
	sentInvites.value = response.toReversed().map(it => new PlanInvite(it));
}

const revokeInvite = async (invite) => {
	loading.value = true;
	await invite.revoke();
	loading.value = false;
}
</script>
