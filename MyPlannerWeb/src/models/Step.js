export default class Step {
	id;
	title;
	description;
	index;

	constructor(step) {
		if (!step) return;
		this.id = step.id;
		this.title = step.title;
		this.description = step.description;
		this.index = step.index;
	}
}
