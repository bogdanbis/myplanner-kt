<template>
	<div v-if="user?.email">
		<h2>My Plans</h2>
		<span class="page-subtitle">The plans you got from other creators.</span>

		<MpCard v-if="pendingInvites.length" title="Received invites">
			<ReceivedPendingInvite
				v-for="invite in pendingInvites"
				:invite="invite"
				@accepted="fetchAcquiredPlans"
				@declined="fetchAcquiredPlans"
			/>
		</MpCard>

		<MpCard v-for="{ plan, id } in user.acquiredPlans" :title="plan.title">
			<span class="mp-card-subtitle">
				<MpIcon icon="person-fill" />
				{{ plan.author.name }}
			</span>
			<MpMultilineText :text="plan.shortDescription" class="text-secondary" />
			<template #actions>
				<MpLink :to="'/my-plans/' + id">View progress</MpLink>
			</template>
		</MpCard>
	</div>
	<MpCard v-else>
		<span>We don't know who you are. 🧐<br />
			Log in to see your plans.</span>
		<LogInButton redirect-to="/my-plans" class="mt-l" />
	</MpCard>
</template>

<script setup>
import LogInButton from '@/components/LogInButton.vue';
import ReceivedPendingInvite from '@/components/plan-progress/ReceivedPendingInvite.vue';
import { useAuthStore } from '@/store/auth.js';
import { computed, onMounted } from 'vue';

const authStore = useAuthStore();
const user = computed(() => authStore.user);

onMounted(async () => {
	if (user.value.acquiredPlans == null)
		await user.value.fetchAcquiredPlans();
	await authStore.fetchReceivedInvites();
})

const fetchAcquiredPlans = async () => {
	await authStore.fetchReceivedInvites();
	await user.value.fetchAcquiredPlans();
}

const pendingInvites = computed(() => {
	return authStore.user?.receivedInvites?.filter(it => it.isPending) || [];
})
</script>
