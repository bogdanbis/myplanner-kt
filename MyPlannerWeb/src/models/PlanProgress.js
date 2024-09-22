import Plan from './Plan.js';
import StepProgress from './StepProgress.js';

export default class PlanProgress {
	id;
	plan = new Plan()
	acquiredAt;
	lastSyncedPlan;
	steps = [];

	constructor(planProgress) {
		if (!planProgress) return;
		this.id = planProgress.id;
		this.plan = new Plan(planProgress.plan);
		this.acquiredAt = planProgress.acquiredAt;
		this.lastSyncedPlan = planProgress.lastSyncedPlan;
		this.steps = planProgress.steps.map(step => new StepProgress(step));
	}

	get updateAvailable() {
		return this.lastSyncedPlan !== this.plan.lastModifiedAt
	}
}
