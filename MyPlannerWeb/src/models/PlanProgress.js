import ApplicationUser from './ApplicationUser.js';
import Plan from './Plan.js';
import StepProgress from './StepProgress.js';
import StepProgressContainer from './StepProgressContainer.js';

export default class PlanProgress extends StepProgressContainer {
	id;
	plan = new Plan()
	participant = new ApplicationUser()
	acquiredAt;
	lastSyncedPlan;
	lastActive;
	comment;

	constructor(planProgress) {
		super(planProgress);
		if (!planProgress) return;
		this.id = planProgress.id;
		this.plan = new Plan(planProgress.plan);
		this.participant = new ApplicationUser(planProgress.participant);
		this.acquiredAt = planProgress.acquiredAt;
		this.lastSyncedPlan = planProgress.lastSyncedPlan;
		this.lastActive = planProgress.lastActive;
		this.comment = planProgress.comment;
		this.completed = planProgress.completed;
		this.steps = planProgress.steps.map(step => new StepProgress(step)) || [];
	}

	get updateAvailable() {
		return this.lastSyncedPlan !== this.plan.lastModifiedAt
	}
}
