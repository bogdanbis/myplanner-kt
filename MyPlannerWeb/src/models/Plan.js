import Task from './Task.js';

export default class Plan {
	id;
	title;
	shortDescription;
	description;
	color = '#5856D6';
	isPublic = false;
	createdAt;
	author;
	tasks = [];

	constructor(plan) {
		if (!plan) return;
		this.id = plan.id;
		this.title = plan.title;
		this.shortDescription = plan.shortDescription;
		this.description = plan.description;
		this.color = plan.color;
		this.isPublic = plan.isPublic;
		this.createdAt = plan.createdAt;
		this.author = plan.author;
		this.tasks = plan.tasks?.map(t => new Task(t)) || [];
	}
}
