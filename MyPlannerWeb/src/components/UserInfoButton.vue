<template>
	<MpLinkButtonWithConfirm
		@confirm="logOut"
		icon="person-circle"
		class="text-white"
		confirm-text="Log Out"
	>
		{{ user.name }}
	</MpLinkButtonWithConfirm>
</template>

<script setup>
import { useAuthStore } from '@/store/auth.js';
import { computed } from 'vue';
import { useToast } from 'vue-toastification';

const user = computed(() => useAuthStore().user);

const authStore = useAuthStore();
const toast = useToast();
const logOut = () => {
	authStore.reset();
	toast.info('You have been logged out.');
	window.location = '/';
}
</script>
