export default class Step {
	id;
	title;
	description;
	index;
	steps = [];

	constructor(step) {
		if (!step) return;
		this.id = step.id;
		this.title = step.title;
		this.description = step.description;
		this.index = step.index;
		this.steps = step.steps?.map(s => new Step(s)) || [];
	}
}
