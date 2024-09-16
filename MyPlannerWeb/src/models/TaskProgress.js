import Task from '@/models/Task.js';

export default class TaskProgress {
	id;
	completed;
	task;

	constructor(taskProgress) {
		if (!taskProgress) return;
		this.id = taskProgress.id;
		this.completed = taskProgress.completed;
		this.task = new Task(taskProgress.task);
	}
}
