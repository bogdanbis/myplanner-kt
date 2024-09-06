<template>
	<i :class="'bi bi-' + icon"></i>
</template>

<script>
export default {
	name: 'MpIcon',
	props: {
		icon: {
			type: String,
			required: true,
		},
	},
}
</script>
