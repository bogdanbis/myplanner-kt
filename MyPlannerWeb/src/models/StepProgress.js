import Step from '@/models/Step.js';

export default class StepProgress {
	id;
	completed;
	step;

	constructor(stepProgress) {
		if (!stepProgress) return;
		this.id = stepProgress.id;
		this.completed = stepProgress.completed;
		this.step = new Step(stepProgress.step);
	}
}
