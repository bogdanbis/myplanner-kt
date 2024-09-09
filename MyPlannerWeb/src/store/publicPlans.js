import { defineStore } from 'pinia';
import api from '../api/index.js';

export const usePlansStore = defineStore('plans', {
	state: () => ({
		publicPlans: null,
	}),

	actions: {
		setPublicPlans(plans) {
			this.publicPlans = plans;
		},
		async fetchPublicPlans() {
			this.publicPlans = await api.get('/plans/browse');
			return this.publicPlans;
		},
	},
})
