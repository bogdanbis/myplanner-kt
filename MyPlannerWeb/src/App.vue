<template>
	<Header />
	<div class="page">
		<div class="content">
			<h1 class="page-title">Browse Plans</h1>
			<PublicPlans />
		</div>
	</div>
</template>

<script setup>
import Header from '@/components/layout/Header.vue';
import PublicPlans from '@/components/PublicPlans.vue';
import { onBeforeMount } from 'vue';
import { useAuthStore } from './store/auth.js';

const authStore = useAuthStore();

onBeforeMount(async () => {
	if (localStorage.getItem('token')) {
		await authStore.fetchUser();
	}
})
</script>
