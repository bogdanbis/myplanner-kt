<template>
	<div class="mp-form-section">
		<MpFormSectionTitle v-if="title">{{ title }}</MpFormSectionTitle>
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
