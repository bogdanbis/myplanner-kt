<template>
	<div class="d-flex align-start" @click="focusStep" v-on-click-outside="unfocusStep">
		<MpButton
			:class="canAddSecondaryStep && step.steps.length > 0 ? '' : 'd-hidden'"
			@click="toggleCollapsed"
			:icon="collapsed ? 'chevron-right' : 'chevron-down'"
			link
		/>
		<div class="w-100">
			<MpFormInput
				:id="'step-title-' + (step.id || step.index)"
				placeholder="Step title"
				unlabeled
				v-model="step.title"
				class="step-title"
				autocomplete="off"
				@focus="focusStep"
			/>
			<MpFormTextarea
				:id="'step-description-' + (step.id || step.index)"
				placeholder="Description"
				unlabeled
				v-model="step.description"
				class="step-description"
				@focus="focusStep"
			/>
		</div>

		<div v-if="showHoverMenu" class="step-actions">
			<div>
				<MpButton v-if="canAddSecondaryStep" @click="addSeconaryStep" icon="plus-lg" link>
					Secondary step
				</MpButton>
				<MpButton v-else @click="addStep" icon="plus-lg" link>
					Step
				</MpButton>
			</div>
			<div class="move-step-actions">
				<MpButton icon="arrow-up" link @click="moveUp" />
				<div class="mx-s">Move</div>
				<MpButton icon="arrow-down" link @click="moveDown" />
			</div>
			<div>
				<MpButton @click="remove" icon="dash" link class="ml-l danger">
					Remove
				</MpButton>
			</div>
		</div>
	</div>

	<div v-auto-animate>
		<StepsFormSection
			v-if="!collapsed & step.steps.length > 0"
			:steps-container="step"
		/>
	</div>
</template>

<script setup>
import StepsFormSection from '@/components/plans/StepsFormSection.vue';
import { useCollapse } from '@/composables/collapse.js';
import Step from '@/models/Step.js';
import { useStepFormStore } from '@/store/stepForm.js';
import { vOnClickOutside } from '@vueuse/components';
import { computed, nextTick } from 'vue';

const props = defineProps({
	step: {
		type: Step,
		required: true,
	},
	canAddSecondaryStep: {
		type: Boolean,
		required: false,
	},
})

const emit = defineEmits(['add-secondary-step', 'add-step', 'remove-step', 'move-up', 'move-down'])

const stepFormStore = useStepFormStore();

const showHoverMenu = computed(() => {
	return stepFormStore.focusedStep === props.step
});

const focusStep = () => {
	stepFormStore.showHoverOn(props.step);
}

const unfocusStep = () => {
	nextTick(() => stepFormStore.hideHover());
}

const addStep = () => {
	emit('add-step')
}

const addSeconaryStep = () => {
	emit('add-secondary-step')
}

const remove = () => {
	emit('remove-step')
}

const moveDown = () => {
	emit('move-down')
}

const moveUp = () => {
	emit('move-up')
}

const { collapsed, toggleCollapsed } = useCollapse(true, false)
</script>
