<template>
	<MpLink v-if="isPending" to="/my-plans" icon="clock">{{ statuses[status] }}</MpLink>
	<span v-else :class="textColor" class="fw-500">
		<MpIcon :icon="icon" />
		{{ statuses[status] }}</span>
</template>

<script setup>
import InviteStatus from '@/models/types/InviteStatus.js';
import { computed } from 'vue';

const statuses = {
	[InviteStatus.PENDING]: 'Pending',
	[InviteStatus.DECLINED]: 'Declined',
	[InviteStatus.ACCEPTED]: 'Accepted',
	[InviteStatus.REVOKED]: 'Revoked',
}

const { status } = defineProps({
	status: {
		type: String,
		required: false,
	},
})

const isPending = computed(() => status === InviteStatus.PENDING);

const textColor = computed(() => {
	switch (status) {
		case InviteStatus.ACCEPTED:
			return 'text-success';
		case InviteStatus.DECLINED:
			return 'text-danger';
	}
})

const icon = computed(() => {
	switch (status) {
		case InviteStatus.ACCEPTED:
			return 'check-lg';
		case InviteStatus.DECLINED:
			return 'x-lg';
	}
})
</script>
