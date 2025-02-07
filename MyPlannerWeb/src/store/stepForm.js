import { defineStore } from 'pinia';

export const useStepFormStore = defineStore('stepForm', {
	state: () => ({
		focusedStep: null,
	}),

	actions: {
		showHoverOn(step) {
			this.focusedStep = step;
		},
		hideHover() {
			this.focusedStep = null;
		},
	},
})
