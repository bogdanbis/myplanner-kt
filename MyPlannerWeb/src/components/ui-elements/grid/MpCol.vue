<template>
	<div class="col" :class="cols ? `col-${colsNumber}` : undefined">
		<slot></slot>
	</div>
</template>

<script setup>
import { computed } from 'vue';

const gridColumns = 60;

const { cols } = defineProps({
	cols: {
		type: [String, Number],
		required: false,
	},
});

const colsNumber = computed(() => {
	if (cols && cols <= 1 && Number.isInteger(gridColumns * cols))
		return cols * gridColumns;
});
</script>
