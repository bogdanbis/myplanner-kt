<template>
	<form :class="rowCols" class="row mp-form" @submit.prevent="$emit('submit', $event)">
		<slot></slot>
		<MpCol cols="1" class="mp-form-actions">
			<slot name="actions"></slot>
		</MpCol>
	</form>
</template>

<script>
export default {
	name: 'MpForm',
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
	emits: ['submit'],

	computed: {
		rowCols() {
			return `row-cols-${this.colsSm} row-cols-md-${this.cols}`
		},
	},
}
</script>
