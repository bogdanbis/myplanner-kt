<template>
	<MpCard v-for="plan in plans" :title="plan.title">
		<span class="mp-card-subtitle">
			<MpIcon icon="person-fill" />
			{{ plan.author.firstName + ' ' + plan.author.lastName }}
			<br />
			<NumberOfParticipants :count="plan.numberOfParticipants" />
		</span>
		<span class="text-secondary">{{ plan.description }}</span>
		<template #actions>
			<div v-if="plan.author.email !== user?.email">
				<MpButton
					v-if="!plan.acquired"
					@click="acquirePlan(plan)"
					:busy="loading"
				>
					Get
				</MpButton>
				<span v-else class="text-primary">
					You have this plan
					<MpIcon icon="check-lg" />
				</span>
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
import { computed, ref } from 'vue';

const { plans } = defineProps({
	plans: {
		type: Array,
		required: true,
	},
})

const authStore = useAuthStore();

const user = computed(() => authStore.user);
const loading = ref(false);

const acquirePlan = async (plan) => {
	if (!user.value)
		return authStore.requestLogin();

	loading.value = true;
	await user.value.acquirePlan(plan);
	user.value.acquiredPlans.forEach(plan => {
		const index = plans.value.findIndex(p => p.id === plan.plan.id)
		if (index >= 0)
			plans.value[index].acquired = true;
	});
	loading.value = false;
}
</script>
