<template>
	<MpCol :cols="cols" :cols-sm="colsSm" class="mp-form-item-container">
		<MpTextarea
			:id="id"
			:label="label"
			:placeholder="placeholder"
			:required="required"
			v-bind="$attrs"
		/>
	</MpCol>
</template>

<script>
import MpCol from '../grid/MpCol.vue';
import MpTextarea from './base/MpTextarea.vue';

export default {
	name: 'MpFormTextarea',
	components: { MpTextarea, MpCol },
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
			required: false,
		},
		required: {
			type: Boolean,
			required: false,
			default: false,
		},
		placeholder: {
			type: String,
			required: false,
		},
	},
}
</script>
