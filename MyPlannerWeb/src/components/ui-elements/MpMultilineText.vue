<template>
	<span v-html="multiline(text)" />
</template>

<script setup>
defineProps({
	text: {
		type: String,
		required: false,
	},
});

const multiline = (text) => {
	if (!text) return;
	return text.replaceAll('\n', '<br/>')
};
</script>
