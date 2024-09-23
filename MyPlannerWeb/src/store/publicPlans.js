import Plan from '@/models/Plan.js';
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
			const response = await api.get('/plans/browse');
			this.publicPlans = response.map(plan => new Plan(plan));
			return this.publicPlans;
		},
	},
})
