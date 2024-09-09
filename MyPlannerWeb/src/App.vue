<template>
	<Layout />
</template>

<script setup>
import Layout from './components/layout/Layout.vue';
import { onBeforeMount } from 'vue';
import { useAuthStore } from './store/auth.js';

const authStore = useAuthStore();

onBeforeMount(async () => {
	if (localStorage.getItem('token')) {
		await authStore.fetchUser();
	}
})
</script>
