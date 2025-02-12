<template>
	<span v-if="withText" class="loading-icon-container"><span class="loading-icon" />Loading...</span>
	<span v-else class="loading-icon" />
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
