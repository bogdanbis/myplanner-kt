export default class StepProgressContainer {
	completed;
	steps = []; // needs to be overwritten

	constructor(container) {
		if (!container) return;
		this.completed = container.completed;
	}

	get completedStepsCount() {
		if (this.steps.length === 0)
			return this.completed ? 1 : 0;
		return this.steps.reduce((acc, step) => acc + step.completedStepsCount, 0)
	}

	get totalStepsCount() {
		if (this.steps.length === 0)
			return 1;
		return this.steps.reduce((acc, step) => acc + step.totalStepsCount, 0)
	}
}
