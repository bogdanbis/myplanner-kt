<template>
	<MpCard v-for="plan in plans" :title="plan.title">
		<span class="mp-card-subtitle">
			<MpIcon icon="person-fill" />
			{{ plan.author.firstName + ' ' + plan.author.lastName }}
		</span>
		<span class="text-secondary">{{ plan.description }}</span>
		<template #actions>
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
		</template>
	</MpCard>
</template>

<script setup>
import { useAuthStore } from '@/store/auth.js';
import { usePlansStore } from '@/store/publicPlans.js';
import { computed, onMounted, ref } from 'vue';

const authStore = useAuthStore();
const plansStore = usePlansStore();

const user = computed(() => authStore.user);
const plans = ref(plansStore.publicPlans);
const loading = ref(false);

onMounted(async () => {
	const publicPlans = await plansStore.fetchPublicPlans();
	if (user.value) {
		if (user.value?.acquiredPlans == null)
			await user.value?.fetchAcquiredPlans();
		const acquiredPlans = user.value.acquiredPlans;
		acquiredPlans.forEach((plan) => {
			const index = publicPlans.findIndex(p => p.id === plan.plan.id)
			if (index >= 0)
				publicPlans[index].acquired = true;
		});
	}
	plans.value = publicPlans;
})

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
