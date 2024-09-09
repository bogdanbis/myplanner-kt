<template>
	<div class="header">
		<div class="header-content">
			<div @click="emit('show-sidebar')" class="menu-button">
				<span><MpIcon icon="list"/></span>
			</div>
			<div class="left-side">
				<LogInButton v-if="!user?.email" />
				<span v-else><MpIcon icon="person-circle" /> {{ user.firstName }}</span>
			</div>
		</div>
		<div class="logo-container">
			<b class="logo">{{ APP_TITLE }}</b>
		</div>
	</div>
</template>

<script setup>
import { APP_TITLE } from '@/router.js';
import { useAuthStore } from '@/store/auth.js';
import { computed } from 'vue';
import LogInButton from '../LogInButton.vue';

const emit = defineEmits(['show-sidebar']);

const user = computed(() => useAuthStore().user);
</script>
