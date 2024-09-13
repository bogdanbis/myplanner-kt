export default class Task {
	id;
	title;
	description;
	index;

	constructor(task) {
		if (!task) return;
		this.id = task.id;
		this.title = task.title;
		this.description = task.description;
		this.index = task.index;
	}
}
