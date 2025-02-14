import { sanitize } from '@/utils/sanitizer.js';

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
		this.sanitize();
	}

	sanitize() {
		this.title = sanitize(this.title);
		this.description = sanitize(this.description);
	}
}
