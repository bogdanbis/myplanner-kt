<template>
	<MpButton v-if="!showInviteForm" @click="showInviteForm = true" icon="send" link>Invite someone</MpButton>
	<MpForm v-else @submit="sendInvite">
		<MpFormInput
			id="invite-participant-email"
			label="Invite someone"
			placeholder="Their email"
			type="email"
			v-model="email"
			required
		/>
		<template #actions>
			<MpButton @click="showInviteForm = false" class="secondary">Cancel</MpButton>
			<MpButton type="submit" icon="send" :disabled="!email">Send invite</MpButton>
		</template>
	</MpForm>
</template>

<script setup>
import api from '@/api/index.js';
import Plan from '@/models/Plan.js';
import { ref } from 'vue';
import { useToast } from 'vue-toastification';

const { plan } = defineProps({
	plan: {
		type: Plan,
		required: true,
	},
});

const toast = useToast();

const showInviteForm = ref(false);
const email = ref('');
const sending = ref(false);

const sendInvite = async () => {
	if (!email.value)
		return;
	sending.value = true;
	try {
		const response = await api.post(`/plans/created/${plan.id}/invite`, null, { params: { email: email.value } });
		if (!response)
			return toast.error('Could not send invite.')
		email.value = '';
		toast('Invite sent to ' + email.value);
		showInviteForm.value = false;
	} finally {
		sending.value = false;
	}
}
</script>
