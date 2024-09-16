<template>
	<div v-if="user?.email">
		<h2>My Plans</h2>
		<span class="page-subtitle">The plans you got from other creators.</span>
		<MpCard v-for="{ plan, id } in user.acquiredPlans" :title="plan.title">
			<span class="mp-card-subtitle">
				<MpIcon icon="person-fill" />
				{{ plan.author?.firstName + ' ' + plan.author?.lastName }}
			</span>
			<br />
			<span class="text-secondary">{{ plan.description }}</span>
			<template #actions>
				<MpLink :to="'/my-plans/' + id">View progress</MpLink>
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
	if (user.value?.acquiredPlans == null)
		await user.value?.fetchAcquiredPlans();
})
</script>
