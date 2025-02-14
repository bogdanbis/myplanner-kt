<template>
	<MpCard v-for="plan in plans" :title="plan.title">
		<div class="mp-card-subtitle">
			<div>
				<MpIcon icon="person-fill" />
				{{ plan.author.firstName + ' ' + plan.author.lastName }}
			</div>
			<br />
			<NumberOfParticipants :count="plan.numberOfParticipants" />
		</div>
		<span class="text-secondary">{{ plan.shortDescription }}</span>
		<template #actions>
			<div v-if="plan.author.email !== authStore.user?.email">
				<MpLink v-if="!plan.acquired" :to="'/plan/' + plan.id">Details</MpLink>
				<MpLink v-else :to="'/my-plans/' + plan.acquired.id" class="text-primary">
					You have this plan
					<MpIcon icon="check-lg" />
				</MpLink>
			</div>
			<div v-else>
				<MpLink :to="'/creator/manage/' + plan.id">Manage</MpLink>
			</div>
		</template>
	</MpCard>
</template>

<script setup>
import NumberOfParticipants from '@/components/NumberOfParticipants.vue';
import { useAuthStore } from '@/store/auth.js';

const { plans } = defineProps({
	plans: {
		type: Array,
		required: true,
	},
})

const authStore = useAuthStore()
</script>
