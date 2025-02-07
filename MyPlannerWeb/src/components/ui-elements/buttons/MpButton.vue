<template>
	<button
		:disabled="busy || disabled"
		:type="type"
		class="mp-button"
		:class="{ 'busy': busy, 'mp-link-button': link, 'mp-icon-button': !$slots.default }"
	>
		<LoadingIcon />
		<MpIcon v-if="icon" :icon="icon" />
		<slot></slot>
	</button>
</template>

<script setup>
import LoadingIcon from '../icons/LoadingIcon.vue';
import MpIcon from '../icons/MpIcon.vue';

defineProps({
	busy: {
		type: Boolean,
		required: false,
		default: false,
	},
	disabled: {
		type: Boolean,
		required: false,
		default: false,
	},
	link: {
		type: Boolean,
		required: false,
		default: false,
	},
	icon: {
		type: String,
		required: false,
	},
	type: {
		type: String,
		required: false,
		default: 'button',
	},
})
</script>
