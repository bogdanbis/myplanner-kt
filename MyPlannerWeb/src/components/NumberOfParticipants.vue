<template>
	{{ count ? count : 'No' }} {{ count === 1 ? 'participant' : 'participants' }}
</template>

<script setup>
const { count } = defineProps({
	count: {
		type: Number,
		required: false,
	},
})
</script>
