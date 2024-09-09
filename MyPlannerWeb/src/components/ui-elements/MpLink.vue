<template>
	<div class="router-link-wrapper">
		<component :is="disabled ? 'span' : 'router-link'" :to="to">
			<MpIcon v-if="icon" :icon="icon" class="link-icon" />
			<slot></slot>
		</component>
	</div>
</template>

<script setup>
import MpIcon from './icons/MpIcon.vue';

defineProps({
	to: {
		type: String,
		required: true,
	},
	disabled: {
		type: Boolean,
		required: false,
		default: false,
	},
	icon: {
		type: String,
		required: false,
	},
})
</script>
