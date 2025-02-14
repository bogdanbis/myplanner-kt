<template>
	<PlansList :plans="plansStore.publicPlans || []" />
</template>

<script setup>
import PlansList from '@/components/explore/PlansList.vue';
import { usePlansStore } from '@/store/publicPlans.js';
import { onMounted } from 'vue';

const plansStore = usePlansStore();

onMounted(async () => {
	if (plansStore.publicPlans == null)
		await plansStore.fetchPublicPlans();
})
</script>
