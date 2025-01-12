<template>
	<LoadingIcon v-if="loading" />
	<Layout v-else />
</template>

<script setup>
import { onBeforeMount, ref } from 'vue';
import Layout from './components/layout/Layout.vue';
import { useAuthStore } from './store/auth.js';
import { useUiStore } from './store/ui.js';

const authStore = useAuthStore();
useUiStore().initPreferedColorScheme();
const loading = ref(true);

onBeforeMount(async () => {
	if (localStorage.getItem('token')) {
		try {
			await authStore.fetchUser();
		} catch (e) {
			authStore.reset();
		}
	}
	loading.value = false;
})

</script>
