<template>
	<div class="mp-card">
		<div v-if="title" class="mp-card-title">
			{{ title }}
		</div>
		<slot></slot>
		<div v-if="slots.actions" class="mp-card-actions">
			<slot name="actions"></slot>
		</div>
	</div>
</template>

<script setup>
import { useSlots } from 'vue'

defineProps({
	title: {
		type: String,
		required: false,
	},
})


const slots = useSlots()
</script>
