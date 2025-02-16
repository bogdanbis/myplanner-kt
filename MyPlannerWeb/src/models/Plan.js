import api from '@/api/index.js';
import { sanitize } from '@/utils/sanitizer.js';
import ApplicationUser from './ApplicationUser.js';
import Step from './Step.js';

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
	images = [];
	steps = [];
	totalSteps;
	stats = {
		numberOfParticipants: null,
		completedStepsCount: null,
	};

	constructor(plan) {
		if (!plan) return;
		this.init(plan);
	}

	init(plan) {
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
		this.images = plan.images.map(img => ({
			...img,
			src: `${api.params.baseURL}/images/${img.id}`,
		}));
		this.steps = plan.steps?.map(t => new Step(t)) || [];
		this.totalSteps = plan.totalSteps;
		if (plan.stats) {
			this.stats = {
				numberOfParticipants: plan.stats.numberOfParticipants,
				completedStepsCount: plan.stats.completedStepsCount,
			};
		}
		this.sanitize();
	}

	async update(plan) {
		const request = new Plan(plan);
		request.sanitize();
		const response = await api.put('/plans/' + this.id, request);
		this.init(response);
	}

	async uploadImage(file) {
		const requestBody = new FormData();
		requestBody.set('photo', file);
		const image = await api.post(`/plans/created/${this.id}/images`, requestBody);
		image.src = `${api.params.baseURL}/images/${image.id}`;
		this.images.push(image);
		return image;
	}

	async deleteImage(image) {
		await api.delete(`/plans/created/${this.id}/images/${image.id}`);
		const idx = this.images.findIndex(it => it.id === image.id);
		if (idx >= 0)
			this.images.splice(idx, 1);
	}

	sanitize() {
		this.title = sanitize(this.title);
		this.shortDescription = sanitize(this.shortDescription);
		this.description = sanitize(this.description);
	}
}
