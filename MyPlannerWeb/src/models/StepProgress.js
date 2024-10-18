import Step from '@/models/Step.js';

export default class StepProgress {
	id;
	completed;
	comment;
	step;
	steps = [];

	constructor(stepProgress) {
		if (!stepProgress) return;
		this.id = stepProgress.id;
		this.completed = stepProgress.completed;
		this.comment = stepProgress.comment;
		this.step = new Step(stepProgress.step);
		this.steps = stepProgress.steps?.map(step => new StepProgress(step)) || [];
	}
}
