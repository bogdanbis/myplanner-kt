<template>
	<MpCard v-for="plan in plans" :title="plan.title">
		<span class="text-secondary">{{ plan.description }}</span>
		<template #actions>
			<MpButton v-if="!plan.acquired" @click="acquirePlan(plan)">Get</MpButton>
			<span v-else class="text-primary">
				<MpIcon icon="person-check-fill" /> You have this plan
			</span>
		</template>
	</MpCard>
</template>

<script setup>
import api from '@/api/index.js';
import { useAuthStore } from '@/store/auth.js';
import { computed, onMounted, ref } from 'vue';

const authStore = useAuthStore();
const user = computed(() => authStore.user);
const plans = ref([]);

onMounted(async () => {
	const publicPlans = await api.get('/plans/browse');
	if (user.value) {
		const acquiredPlans = await user.value.fetchAcquiredPlans();
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
		return authStore.requestLogin()

	await user.value.acquirePlan(plan);
	user.value.acquiredPlans.forEach((plan) => {
		const index = plans.value.findIndex(p => p.id === plan.plan.id)
		if (index >= 0)
			plans.value[index].acquired = true;
	});
}
</script>
