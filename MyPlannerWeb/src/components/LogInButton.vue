<template>
	<MpDialog ref="dialog" title="Log In" size="small">
		<MpForm cols="1" @submit="logIn">
			<MpFormInput id="email" type="email" placeholder="Email" v-model="email" />
			<MpFormInput id="password" type="password" placeholder="Password" v-model="password" />
			<template #actions>
				<MpButton type="submit" :disabled="!Boolean(email && password)" :busy="loading">Log In</MpButton>
			</template>
		</MpForm>
	</MpDialog>
	<MpButton class="log-in-button" link @click="openLoginDialog">
		Log In
	</MpButton>
</template>

<script setup>
import Api from '@/api/Api.js';
import api from '@/api/index.js';
import { ref, useTemplateRef, watch } from 'vue';
import { useToast } from 'vue-toastification';
import { useAuthStore } from '../store/auth.js';

const authStore = useAuthStore();

const toast = useToast()
const loading = ref(false);
const dialog = useTemplateRef('dialog');
const email = ref('');
const password = ref('');

const openLoginDialog = () => {
	dialog.value.open()
}

const logIn = async () => {
	loading.value = true;
	try {
		await api.logIn(email.value, password.value);
		window.location = '/';
	} catch (e) {
		const error_type = e.response?.data.error_type;
		if (error_type === 'user_not_found')
			toast.error(Api.errorTypes.user_not_found);
		else
			toast.error('Parolă incorectă.');
	}
	loading.value = false;
}

watch(() => authStore.requireLogin, async (newVal) => {
	if (newVal)
		openLoginDialog()
})
</script>
