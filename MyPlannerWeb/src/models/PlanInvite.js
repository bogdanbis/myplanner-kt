import ApplicationUser from '@/models/ApplicationUser.js';
import Plan from '@/models/Plan.js';
import InviteStatus from '@/models/types/InviteStatus.js';

export default class PlanInvite {
	id;
	status;
	createdAt;
	respondedAt;
	plan;
	sender;
	recipient;

	constructor(response) {
		if (!response) return;
		this.id = response.id;
		this.status = response.status;
		this.createdAt = response.createdAt;
		this.respondedAt = response.respondedAt;
		this.plan = new Plan(response.plan);
		this.sender = new ApplicationUser(response.sender);
		this.recipient = new ApplicationUser(response.recipient);
	}

	get isPending() {
		return this.status === InviteStatus.PENDING;
	}
}
