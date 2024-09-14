<template>
	<div v-if="user?.email">
		<h2>Created Plans</h2>
		<span class="page-subtitle">Plans that you have created. Add a new one or edit an existing one.</span>
		<MpLink icon="plus-circle" to="/creator/new">Create a new Plan</MpLink>
		<MpCard v-for="plan in user.createdPlans" :title="plan.title">
			<span class="text-secondary">{{ plan.description }}</span>
			<template #actions>
				<MpLink :to="'/creator/details/' + plan.id">Manage</MpLink>
			</template>
		</MpCard>
	</div>
	<MpCard v-else>
		<span>We don't know who you are. 🧐<br />
			Log in to see your plans.</span>
		<LogInButton redirect-to="/my-plans" class="m-top-l" />
	</MpCard>
</template>

<script setup>
import LogInButton from '@/components/LogInButton.vue';
import { useAuthStore } from '@/store/auth.js';
import { computed, onMounted } from 'vue';

const authStore = useAuthStore();
const user = computed(() => authStore.user);

onMounted(async () => {
	if (user.value?.createdPlans == null)
		await user.value?.fetchCreatedPlans();
})
</script>
