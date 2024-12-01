<template>
	<div class="mp-inline-value-container" :class="{ 'no-space-between': noSpaceBetween }">
		<span class="mp-inline-label"><slot name="label">{{ label }}</slot></span>
		<span class="mp-inline-value"><slot>{{ value }}</slot></span>
	</div>
</template>

<script setup>
defineProps({
	label: {
		type: String,
		required: false,
	},
	noSpaceBetween: {
		type: Boolean,
		required: false,
		default: false,
	},
	value: {
		required: false,
	},
})
</script>
