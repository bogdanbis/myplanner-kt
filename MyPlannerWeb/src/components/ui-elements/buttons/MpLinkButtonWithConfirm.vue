<template>
	<MpButton
		v-if="!showConfirm"
		@click="showConfirm = true"
		:id="id"
		link
		:busy="busy"
	>
		<slot></slot>
	</MpButton>
	<div v-else :id="id" class="mp-button-confirm-container">
		<MpButton @click="showConfirm = false" :icon="cancelIcon" link class="dark">
			Cancel
		</MpButton>
		<MpButton @click="confirm" :icon="confirmIcon" link class="danger" :busy="busy">
			Confirm
		</MpButton>
	</div>
</template>

<script setup>
import { computed, ref } from 'vue';
import MpButton from './MpButton.vue';

const props = defineProps({
	busy: {
		type: Boolean,
		required: false,
		default: false,
	},
	id: {
		required: false,
		default: 'confirm-button',
	},
	withIcons: {
		type: Boolean,
		required: false,
		default: false,
	},
});

const emit = defineEmits(['confirm']);

const showConfirm = ref(false);

const confirmIcon = computed(() => {
	return props.withIcons ? 'exclamation-circle-fill' : null;
});

const cancelIcon = computed(() => {
	return props.withIcons ? 'slash-circle' : null;
})

const confirm = () => {
	showConfirm.value = false;
	emit('confirm');
}
</script>
