<template>
	<div :class="rowCols" class="row">
		<slot></slot>
	</div>
</template>

<script>
export default {
	name: 'MpRow',
	props: {
		cols: {
			type: [String, Number],
			required: false,
			default: 1,
		},
		colsSm: {
			type: [String, Number],
			required: false,
			default: 1,
		},
	},

	computed: {
		rowCols() {
			return `row-cols-${this.colsSm} row-cols-md-${this.cols}`
		},
	},
}
</script>
