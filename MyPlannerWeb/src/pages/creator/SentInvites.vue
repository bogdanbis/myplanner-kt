<template>
	<h2>Sent Invites</h2>

	<MpCard title="Sent invites">
		<MpTable :fields="tableFields" :busy="loading" :empty="sentInvites.length === 0">
			<tr v-for="invite in sentInvites">
				<td class="line-height-normal">
					<span>{{ invite.recipient.name }}<br /></span>
					<span class="text-secondary font-size-s">{{ invite.recipient.email }}</span>
				</td>
				<td>
					<MpLink :to="`/creator/manage/${invite.plan.id}`">{{ invite.plan.title }}</MpLink>
				</td>
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
import PlanInvite from '@/models/PlanInvite.js';
import { onMounted, ref } from 'vue';

const tableFields = [
	{ label: 'Sent to' },
	{ label: 'Plan' },
	{ label: 'Status' },
	{ label: 'Responded at' },
	{ label: 'Sent at' },
]

const sentInvites = ref([]);
const loading = ref(false);

onMounted(async () => {
	loading.value = true;
	await fetchInvites();
	loading.value = false;
})

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
