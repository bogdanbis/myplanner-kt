<template>
	<div :class="`col-${this.colsSm} ${colsClass}`">
		<slot></slot>
	</div>
</template>

<script>
const gridColumns = 12;

export default {
	name: 'MpCol',
	props: {
		cols: {
			type: [String, Number],
			required: false,
		},
		colsSm: {
			type: [String, Number],
			required: false,
			default: gridColumns,
		},
	},
	computed: {
		colsNumber() {
			if (this.cols && this.cols <= 1 && Number.isInteger(gridColumns * this.cols))
				return this.cols * gridColumns;
			return null;
		},
		colsClass() {
			if (!this.colsNumber)
				return '';
			return 'col-md-' + this.colsNumber;
		},
	},
}
</script>
