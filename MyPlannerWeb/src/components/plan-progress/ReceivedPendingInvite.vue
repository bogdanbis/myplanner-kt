<template>
	<div class="mp-form-subsection">
		<div class="mb-m">
			<h5 class="mt-0 mb-xs">{{ invite.plan.title }}</h5>
			<MpIcon icon="person-fill" />
			{{ invite.plan.author.name }}
		</div>
		<span>
			Invite sent by {{ invite.sender.name }}</span>
		<br />
		<span>Received at {{ $relativeDate(invite.createdAt) }}</span>
		<MpButton v-if="!showActions" @click="showActions = true" class="mt-m">Respond</MpButton>
		<div v-if="showActions" class="d-flex mt-m">
			<MpButton @click="showActions = false" :busy="loading" class="secondary">
				Cancel
			</MpButton>
			<MpButton @click="decline" :busy="loading" link class="danger" icon="x-lg">
				Decline invite
			</MpButton>
			<MpButton @click="accept" :busy="loading" link class="success" icon="check-lg">
				Accept invite
			</MpButton>
		</div>
	</div>
</template>

<script setup>
import api from '@/api/index.js';
import PlanInvite from '@/models/PlanInvite.js';
import { ref } from 'vue';

const { invite } = defineProps({
	invite: {
		type: PlanInvite,
		required: true,
	},
})

const emit = defineEmits(['declined', 'accepted'])

const showActions = ref(false);
const loading = ref(false);

const decline = async () => {
	loading.value = true;
	await api.post(`/invites/pending/${invite.id}/decline`);
	emit('declined');
	showActions.value = false;
	loading.value = false;
}

const accept = async () => {
	loading.value = true;
	await api.post(`/invites/pending/${invite.id}/accept`);
	emit('accepted');
	showActions.value = false;
	loading.value = false;
}
</script>
