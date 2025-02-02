<template>
	<div v-if="user?.email">
		<h2>My Plans</h2>
		<span class="page-subtitle">The plans you got from other creators.</span>

		<MpCard v-if="pendingInvites.length" title="Received invites">
			<div v-for="invite in pendingInvites" class="mp-form-subsection">
				<b>{{ invite.plan.title }}</b><br />
				<span>
					<MpIcon icon="person-fill" />
					{{ invite.plan.author.name }}</span><br />
				<span>Received at: {{ $relativeDate(invite.createdAt) }}</span>
			</div>
		</MpCard>

		<MpCard v-for="{ plan, id } in user.acquiredPlans" :title="plan.title">
			<span class="mp-card-subtitle">
				<MpIcon icon="person-fill" />
				{{ plan.author.name }}
			</span>
			<span class="text-secondary">{{ plan.description }}</span>
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
import { useAuthStore } from '@/store/auth.js';
import { computed, onMounted } from 'vue';

const authStore = useAuthStore();
const user = computed(() => authStore.user);

onMounted(async () => {
	if (user.value.acquiredPlans == null)
		await user.value.fetchAcquiredPlans();
	await user.value.fetchReceivedInvites();
})

const pendingInvites = computed(() => {
	return user.value.receivedInvites?.filter(it => it.isPending) || [];
})
</script>
