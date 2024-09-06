<template>
	<div class="card">
		<div v-if="title" class="card-title">
			<h2>{{ title }}</h2>
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
