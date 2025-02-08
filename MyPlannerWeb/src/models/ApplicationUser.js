import api from '@/api/index.js';
import PlanInvite from '@/models/PlanInvite.js';
import PlanInviteStatus from '@/models/types/PlanInviteStatus.js';
import Plan from './Plan.js';

export default class ApplicationUser {
	firstName;
	lastName;
	name;
	email;
	acquiredPlans;
	uiPreferences = { pinnedPlans: null };
	receivedInvites;
	pendingInvites;

	constructor(userResponse) {
		if (!userResponse) return;
		this.firstName = userResponse.firstName;
		this.lastName = userResponse.lastName;
		this.name = userResponse.name;
		this.email = userResponse.email;
	}

	async fetchUIPreferences() {
		const { pinnedPlans } = await api.get('/ui-preferences');
		this.uiPreferences = { pinnedPlans: JSON.parse(pinnedPlans) };
	}

	async fetchAcquiredPlans() {
		this.acquiredPlans = await api.get('/plans/acquired');
		return this.acquiredPlans;
	}

	async fetchCreatedPlans() {
		const plans = await api.get('/plans/created');
		this.createdPlans = plans.map(p => new Plan(p));
	}

	async fetchReceivedInvites() {
		const invites = await api.get('/invites/received');
		this.receivedInvites = invites.map(i => new PlanInvite(i));
		this.pendingInvites = this.receivedInvites.filter(it => it.status === PlanInviteStatus.PENDING);
	}

	async acquirePlan(plan) {
		if (this.acquiredPlans == null)
			await this.fetchAcquiredPlans();
		const planProgress = await api.post(`/plans/${plan.id}/acquire`);
		if (this.acquiredPlans)
			this.acquiredPlans.unshift(planProgress);
	}
}
