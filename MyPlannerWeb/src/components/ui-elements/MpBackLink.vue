<template>
	<MpLink :to="to" class="cursor-pointer m-top-l">
		<MpIcon :icon="icon" /> <slot></slot>
	</MpLink>
</template>

<script setup>
defineProps({
	to: {
		type: String,
		required: true,
	},
	icon: {
		type: String,
		required: false,
		default: 'chevron-left',
	},
})
</script>
