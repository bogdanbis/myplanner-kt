<template>
	<span v-html="multiline" />
</template>

<script setup>
import { sanitize } from '@/utils/sanitizer.js';
import { computed } from 'vue';

const { text } = defineProps({
	text: {
		type: String,
		required: false,
	},
});

const multiline = computed(() => {
	if (!text) return;
	return sanitize(text).replaceAll('\n', '<br />');
});
</script>
