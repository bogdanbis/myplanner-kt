<template>
	<MpLink :to="'/my-plans/' + plan.id">View progress</MpLink>
</template>

<script setup>
import Plan from '@/models/Plan.js';

defineProps({
	plan: {
		type: Plan,
		required: true,
	},
})
</script>
