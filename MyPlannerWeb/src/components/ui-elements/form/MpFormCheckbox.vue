<template>
	<MpCol :cols="cols" :cols-sm="colsSm" class="mp-form-check-col">
		<MpCheckbox :id="id" :label="label" v-bind="$attrs" />
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
		id: {
			type: String,
			required: true,
		},
		cols: {
			type: [String, Number],
			required: false,
		},
		colsSm: {
			type: [String, Number],
			required: false,
		},
		label: {
			type: String,
			required: true,
		},

	},
}
</script>
