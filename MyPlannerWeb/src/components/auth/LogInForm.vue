<template>
	<MpForm cols="1" @submit.prevent="logIn">
		<MpFormInput id="email" type="email" placeholder="Email" v-model="email" />
		<MpFormInput id="password" type="password" placeholder="Password" v-model="password" />
		<template #actions>
			<MpButton type="submit" :disabled="!Boolean(email && password)" :busy="loading">
				Log In
			</MpButton>
		</template>
	</MpForm>
</template>

<script setup>
import Api from '@/api/Api.js';
import api from '@/api/index.js';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useToast } from 'vue-toastification';

const route = useRoute();

const toast = useToast();

const loading = ref(false);
const email = ref('');
const password = ref('');

const logIn = async () => {
	loading.value = true;
	try {
		await api.logIn(email.value, password.value);
		window.location = route.fullPath;
	} catch (e) {
		const error_type = e.response?.data.error_type;
		if (error_type === 'user_not_found')
			toast.error(Api.errorTypes.user_not_found);
		else
			toast.error('Parolă incorectă.');
	}
	loading.value = false;
}
</script>
