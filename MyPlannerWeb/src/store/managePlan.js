import api from '@/api/index.js';
import Plan from '@/models/Plan.js';
import { defineStore } from 'pinia';

export const useManagePlanStore = defineStore('managePlans', {
	state: () => ({
		plan: new Plan(),
		sentInvites: null,
		participants: null,
	}),

	actions: {
		async fetchPlan(planId) {
			const response = await api.get('/plans/created/' + planId);
			this.plan = new Plan(response);
		},
	},
})
