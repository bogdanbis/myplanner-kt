<template>
	<MpCol :cols :cols-sm class="mp-form-check-col">
		<MpCheckbox :disabled :id :label v-bind="$attrs" />
	</MpCol>
</template>

<script>
import MpCol from '../grid/MpCol.vue';
import MpCheckbox from './base/MpCheckbox.vue';

export default {
	name: 'MpFormCheckbox',
	inheritAttrs: false,
	components: { MpCol, MpCheckbox },
	props: {
		cols: {
			type: [String, Number],
			required: false,
		},
		colsSm: {
			type: [String, Number],
			required: false,
		},
		disabled: {
			type: Boolean,
			required: false,
			default: false,
		},
		id: {
			type: String,
			required: true,
		},
		label: {
			type: String,
			required: true,
		},

	},
}
</script>
