<template>
	<h2>Received Invites</h2>

	<MpCard>
		<MpCheckbox id="show-all-checkbox" label="Pending only" v-model="showPendingOnly" />
		<MpTable :fields="tableFields" :busy="loading" :empty="shownInvites.length === 0" class="mt-s">
			<tr v-for="invite in shownInvites">
				<td class="line-height-normal">
					<span>{{ invite.sender.name }}<br /></span>
					<span class="text-secondary font-size-s">{{ invite.sender.email }}</span>
				</td>
				<td>{{ invite.plan.title }}</td>
				<td>{{ $relativeDate(invite.respondedAt) || '-' }}</td>
				<td>{{ $relativeDate(invite.createdAt) }}</td>
				<td>
					<InviteStatus :status="invite.status" />
				</td>
			</tr>
		</MpTable>
	</MpCard>
</template>

<script setup>
import api from '@/api/index.js';
import InviteStatus from '@/components/plans/InviteStatus.vue';
import PlanInvite from '@/models/PlanInvite.js';
import { computed, onMounted, ref } from 'vue';

const tableFields = [
	{ label: 'From' },
	{ label: 'Plan' },
	{ label: 'Responded at' },
	{ label: 'Received at' },
	{ label: 'Status' },
];

const receivedInvites = ref([]);
const showPendingOnly = ref(false);
const loading = ref(false);

const shownInvites = computed(() => {
	if (showPendingOnly.value)
		return receivedInvites.value.filter(it => it.isPending);
	return receivedInvites.value;
})

onMounted(async () => {
	loading.value = true;
	await fetchInvites();
	loading.value = false;
})

const fetchInvites = async () => {
	const response = await api.get('/invites/received');
	receivedInvites.value = response.map(it => new PlanInvite(it));
}
</script>
