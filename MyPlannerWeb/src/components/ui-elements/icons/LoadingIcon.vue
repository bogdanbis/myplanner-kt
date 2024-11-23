<template>
	<span class="loading-icon">{{ withText ? 'Loading...' : null }}</span>
</template>

<script setup>
defineProps({
	withText: {
		type: Boolean,
		required: false,
		default: false,
	},
})
</script>
