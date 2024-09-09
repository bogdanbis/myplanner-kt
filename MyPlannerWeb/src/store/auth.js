import api from '@/api';
import ApplicationUser from '@/models/ApplicationUser';
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
	state: () => ({
		user: null,
		requireLogin: false,
	}),

	actions: {
		setUser(user) {
			this.user = user;
		},
		async fetchUser() {
			const userResponse = await api.get('/whoami');
			this.setUser(new ApplicationUser(userResponse));
		},
		requestLogin() {
			this.requireLogin = true;
		},
	},
})
