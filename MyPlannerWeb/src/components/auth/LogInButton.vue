<template>
	<MpDialog ref="dialog" :title="showLogInForm ? 'Log In' : 'Sign Up'" size="small">
		<LogInForm v-if="showLogInForm" />
		<SignUpForm v-else />
		<div class="d-flex align-center" v-if="showLogInForm">
			Don't have an account?
			<MpButton link @click="showLogInForm = !showLogInForm" class="ml-xs">
				Sign up
			</MpButton>
		</div>
		<div class="d-flex align-center" v-else>
			Already have an account?
			<MpButton link @click="showLogInForm = !showLogInForm" class="ml-xs">
				Log In
			</MpButton>
		</div>
	</MpDialog>
	<MpButton class="log-in-button" :class="$attrs.class" @click="openLoginDialog">
		Log In
	</MpButton>
</template>

<script setup>
import { useAuthStore } from '@/store/auth.js';
import { ref, useTemplateRef, watch } from 'vue';
import LogInForm from './LogInForm.vue';
import SignUpForm from './SignUpForm.vue';

const authStore = useAuthStore();
const dialog = useTemplateRef('dialog');

const showLogInForm = ref(true);

const openLoginDialog = () => {
	showLogInForm.value = true;
	dialog.value.open()
}

watch(() => authStore.requireLogin, async (newVal) => {
	if (newVal)
		openLoginDialog()
})
</script>
