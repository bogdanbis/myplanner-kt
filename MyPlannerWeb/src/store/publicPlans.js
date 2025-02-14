import Plan from '@/models/Plan.js';
import { useAuthStore } from '@/store/auth.js';
import { defineStore } from 'pinia';
import api from '../api/index.js';

export const usePlansStore = defineStore('plans', {
	state: () => ({
		publicPlans: null,
	}),

	actions: {
		async fetchPublicPlans() {
			const response = await api.get('/plans/browse');
			const user = useAuthStore().user;
			if (!user) {
				this.publicPlans = response.map(it => new Plan(it));
			} else {
				if (user.acquiredPlans == null)
					await user.fetchAcquiredPlans();
				this.publicPlans = response.map(p => {
					const plan = new Plan(p);
					plan.acquired = user.acquiredPlans.find(it => it.plan.id === plan.id);
					return plan;
				});
			}
		},
	},
})
