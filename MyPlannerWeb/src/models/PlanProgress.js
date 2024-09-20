import Plan from './Plan.js';
import StepProgress from './StepProgress.js';

export default class PlanProgress {
	id;
	plan = new Plan()
	acquiredAt;
	steps = [];

	constructor(planProgress) {
		if (!planProgress) return;
		this.id = planProgress.id;
		this.plan = new Plan(planProgress.plan);
		this.acquiredAt = planProgress.acquiredAt;
		this.steps = planProgress.steps.map(step => new StepProgress(step));
	}
}
