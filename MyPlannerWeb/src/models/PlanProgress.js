import Plan from './Plan.js';
import TaskProgress from './TaskProgress.js';

export default class PlanProgress {
	id;
	plan = new Plan()
	acquiredAt;
	tasks = [];

	constructor(planProgress) {
		if (!planProgress) return;
		this.id = planProgress.id;
		this.plan = new Plan(planProgress.plan);
		this.acquiredAt = planProgress.acquiredAt;
		this.tasks = planProgress.tasks.map(task => new TaskProgress(task));
	}
}
