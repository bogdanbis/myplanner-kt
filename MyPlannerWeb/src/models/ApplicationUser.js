import api from '@/api/index.js';

export default class ApplicationUser {
	firstName;
	lastName;
	email;
	acquiredPlans;

	constructor(logInResponse) {
		if (!logInResponse) return;
		this.firstName = logInResponse.firstName;
		this.lastName = logInResponse.lastName;
		this.email = logInResponse.email;
	}

	get fullName() {
		return this.firstName + ' ' + this.lastName;
	}

	async fetchAcquiredPlans() {
		this.acquiredPlans = await api.get('/plans/acquired');
		return this.acquiredPlans;
	}

	async acquirePlan(plan) {
		if (this.acquiredPlans == null)
			await this.fetchAcquiredPlans();
		const planProgress = await api.post(`/plans/${plan.id}/acquire`);
		if (this.acquiredPlans)
			this.acquiredPlans.unshift(planProgress);
	}
}
