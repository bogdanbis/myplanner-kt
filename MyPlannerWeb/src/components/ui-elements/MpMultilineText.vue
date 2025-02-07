<template>
	<span v-html="multiline" />
</template>

<script setup>
import DOMPurify from 'dompurify';
import { computed } from 'vue';

const { text } = defineProps({
	text: {
		type: String,
		required: false,
	},
});

const multiline = computed(() => {
	if (!text) return;
	return DOMPurify.sanitize(text, { ALLOWED_TAGS: [] })
			.replaceAll('\n', '<br/>')
});
</script>
