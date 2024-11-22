<template>
	<div class="progress-bar">
		<div class="progress-bar-inner" :class="'progress-bar-w-' + percentage" />
		<span>
			<slot>{{ percentage }}%</slot>
		</span>
	</div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
	count: {
		type: [Number, String],
		required: true,
	},
	total: {
		type: [Number, String],
		required: true,
	},
})

const percentage = computed(() => {
	return Math.round(props.count / props.total * 100)
})
</script>
