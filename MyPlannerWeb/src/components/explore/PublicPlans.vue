<template>
	<PlansList :plans="plans" />
</template>

<script setup>
import PlansList from '@/components/explore/PlansList.vue';
import { useAuthStore } from '@/store/auth.js';
import { usePlansStore } from '@/store/publicPlans.js';
import { computed, onMounted, ref } from 'vue';

const authStore = useAuthStore();
const plansStore = usePlansStore();

const user = computed(() => authStore.user);
const plans = ref(plansStore.publicPlans);

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
</script>
