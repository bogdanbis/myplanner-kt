import api from '@/api/index.js';
import Plan from './Plan.js';

export default class ApplicationUser {
	firstName;
	lastName;
	name;
	email;
	acquiredPlans;
	uiPreferences = { pinnedPlans: null };

	constructor(userResponse) {
		if (!userResponse) return;
		this.firstName = userResponse.firstName;
		this.lastName = userResponse.lastName;
		this.name = userResponse.name;
		this.email = userResponse.email;
		this.uiPreferences = { pinnedPlans: JSON.parse(userResponse.uiPreferences.pinnedPlans) };
	}

	async fetchAcquiredPlans() {
		this.acquiredPlans = await api.get('/plans/acquired');
		return this.acquiredPlans;
	}

	async fetchCreatedPlans() {
		const plans = await api.get('/plans/created');
		this.createdPlans = plans.map(p => new Plan(p));
		return this.createdPlans;
	}

	async acquirePlan(plan) {
		if (this.acquiredPlans == null)
			await this.fetchAcquiredPlans();
		const planProgress = await api.post(`/plans/${plan.id}/acquire`);
		if (this.acquiredPlans)
			this.acquiredPlans.unshift(planProgress);
	}
}
