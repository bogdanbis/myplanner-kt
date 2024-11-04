import Step from './Step.js';
import StepProgressContainer from './StepProgressContainer.js';

export default class StepProgress extends StepProgressContainer {
	id;
	comment;
	step;
	planProgress;

	constructor(stepProgress, planProgress) {
		super(stepProgress);
		if (!stepProgress) return;
		this.id = stepProgress.id;
		this.comment = stepProgress.comment;
		this.step = new Step(stepProgress.step);
		this.planProgress = planProgress;
		this.steps = stepProgress.steps?.map(step => new StepProgress(step)) || [];
	}
}
