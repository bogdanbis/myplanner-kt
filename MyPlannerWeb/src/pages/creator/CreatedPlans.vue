<template>
	<div v-if="user?.email">
		<h2>Created Plans</h2>
		<span class="page-subtitle">Plans that you have created. Add a new one or edit an existing one.</span>
		<MpLink icon="plus-circle" to="/creator/new" class="m-bottom-l">Create a new Plan</MpLink>

		<MpCard v-for="plan in user.createdPlans" :title="plan.title">
			<div class="w-30-desktop">
				<MpInlineValue label="Participants" :value="plan.numberOfParticipants" />
				<MpInlineValue label="Last modified:" :value="$relativeDate(plan.lastModifiedAt)" />
				<MpInlineValue label="Visibility:" :value="plan.isPublic ? 'Public' : 'Private'" />
			</div>
			<template #actions>
				<MpLink :to="'/creator/manage/' + plan.id">Manage</MpLink>
			</template>
		</MpCard>
	</div>
	<MpCard v-else>
		<span>We don't know who you are. 🧐<br />
			Log in to see your plans.</span>
		<LogInButton redirect-to="/creator" class="m-top-l" />
	</MpCard>
</template>

<script setup>
import LogInButton from '@/components/LogInButton.vue';
import { useAuthStore } from '@/store/auth.js';
import { computed, onMounted } from 'vue';

const authStore = useAuthStore();
const user = computed(() => authStore.user);

onMounted(async () => {
	await user.value?.fetchCreatedPlans();
})
</script>
