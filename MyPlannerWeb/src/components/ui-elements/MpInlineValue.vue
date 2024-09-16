<template>
	<div class="mp-inline-value-container">
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
	value: {
		required: false,
	},
})
</script>
