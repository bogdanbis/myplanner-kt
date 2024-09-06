<template>
	<div class="card">
		<div v-if="title" class="card-title">
			{{ title }}
		</div>
		<slot></slot>
	</div>
</template>

<script setup>
defineProps({
	title: {
		type: String,
		required: false,
	},
})
</script>
