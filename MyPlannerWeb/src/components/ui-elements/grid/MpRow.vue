<template>
	<div class="row" :class="`row-cols-${cols}`">
		<slot></slot>
	</div>
</template>

<script setup>
const { cols } = defineProps({
	cols: {
		type: [String, Number],
		required: false,
		default: 1,
	},
});
</script>
