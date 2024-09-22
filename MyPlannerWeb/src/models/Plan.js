import Step from './Step.js';
import ApplicationUser from './ApplicationUser.js';

export default class Plan {
	id;
	title;
	shortDescription;
	description;
	color = '#5856D6';
	isPublic = false;
	createdAt;
	lastModifiedAt;
	numberOfParticipants;
	author = new ApplicationUser();
	steps = [];
	stats = {
		numberOfParticipants: null,
		completedStepsCount: null,
	};

	constructor(plan) {
		if (!plan) return;
		this.id = plan.id;
		this.title = plan.title;
		this.shortDescription = plan.shortDescription;
		this.description = plan.description;
		this.color = plan.color;
		this.isPublic = plan.isPublic;
		this.createdAt = plan.createdAt;
		this.lastModifiedAt = plan.lastModifiedAt;
		this.numberOfParticipants = plan.numberOfParticipants;
		this.author = new ApplicationUser(plan.author);
		this.steps = plan.steps?.map(t => new Step(t)) || [];
		if (plan.stats) {
			this.stats = {
				numberOfParticipants: plan.stats.numberOfParticipants,
				completedStepsCount: plan.stats.completedStepsCount,
			};
		}
	}
}
