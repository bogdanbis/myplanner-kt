<template>
	<LoadingIcon v-if="loading" />
	<Layout v-else />
</template>

<script setup>
import Layout from './components/layout/Layout.vue';
import { onBeforeMount, ref } from 'vue';
import { useAuthStore } from './store/auth.js';
import { useUiStore } from './store/ui.js';

const authStore = useAuthStore();
useUiStore().initPreferedColorScheme();
const loading = ref(true);

onBeforeMount(async () => {
	if (localStorage.getItem('token')) {
		await authStore.fetchUser();
	}
	loading.value = false;
})

</script>
