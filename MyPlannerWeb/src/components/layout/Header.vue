<template>
	<div class="header-placeholder"></div>
	<div class="header">
		<div class="header-logo">Progress Planner</div>
		<div class="header-actions">
			<LogInButton v-if="!user?.email" />
			<span v-else>Welcome, {{ user.firstName }}!</span>
		</div>
	</div>
</template>

<script setup>
import { useAuthStore } from '@/store/auth.js';
import { computed } from 'vue';
import LogInButton from '../LogInButton.vue';

const user = computed(() => useAuthStore().user)
</script>
