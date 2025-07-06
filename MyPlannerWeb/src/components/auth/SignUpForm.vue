<template>
	<MpForm cols="1" @submit.prevent="signUp">
		<MpFormInput
			id="email"
			type="email"
			placeholder="Email"
			v-model="user.email"
			required
		/>
		<MpFormInput
			id="first-name"
			placeholder="First name"
			v-model="user.firstName"
			:cols="1/2"
			required
		/>
		<MpFormInput
			id="last-name"
			placeholder="Last name"
			v-model="user.lastName"
			:cols="1/2"
			required
		/>
		<MpFormInput
			id="password"
			type="password"
			placeholder="Password"
			v-model="user.password"
			required
		/>
		<MpFormInput
			id="password-confirm"
			type="password"
			placeholder="Confirm password"
			v-model="user.passwordConfirm"
			required
		/>
		<template #actions>
			<MpButton type="submit" :disabled="!maySubmit" :busy="loading">
				Sign Up
			</MpButton>
		</template>
	</MpForm>
</template>

<script setup>
import api from '@/api/index.js';
import { computed, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const loading = ref(false);
const user = ref({
	email: null,
	firstName: null,
	lastName: null,
	password: null,
	passwordConfirm: null,
});

const maySubmit = computed(() => Boolean(
		user.value.email
		&& user.value.firstName && user.value.lastName
		&& user.value.password && user.value.passwordConfirm
		&& user.value.password === user.value.passwordConfirm,
))

const signUp = async () => {
	loading.value = true;
	await api.signUp(user.value);
	window.location = route.fullPath;
	loading.value = false;
}
</script>
