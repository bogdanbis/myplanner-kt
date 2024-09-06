import { defineStore } from 'pinia';
import { useAuthStore } from './auth.js';

const useStore = defineStore('$store', {
	state: () => ({
		$auth: useAuthStore(),
	}),

	actions: {
		reset() {
			this.$auth.$reset();
		},
	},
})
