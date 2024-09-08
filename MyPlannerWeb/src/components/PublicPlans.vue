<template>
	<MpCard v-for="plan in plans" :title="plan.title">
		<span class="author-name">
			<MpIcon icon="person-fill" />
			{{ plan.author.firstName + ' ' + plan.author.lastName }}
		</span>
		<br />
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
				You have this plan <MpIcon icon="check-lg" />
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
const loading = ref(false);

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
		return authStore.requestLogin();

	loading.value = true;
	await user.value.acquirePlan(plan);
	user.value.acquiredPlans.forEach((plan) => {
		const index = plans.value.findIndex(p => p.id === plan.plan.id)
		if (index >= 0)
			plans.value[index].acquired = true;
	});
	loading.value = false;
}
</script>
