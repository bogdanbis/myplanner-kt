<template>
	<MpCol :cols="cols" :cols-sm="colsSm" class="mp-form-item-container">
		<MpNumberInput
			:id="id"
			:label="label"
			:placeholder="placeholder"
			:required="required"
			v-bind="$attrs"
		/>
		<small v-if="info"><MpIcon icon="info-circle" class="me-2" />{{ info }}</small>
		<small v-if="description">{{ description }}</small>
	</MpCol>
</template>

<script>
import MpCol from '../grid/MpCol.vue';
import MpIcon from '../icons/MpIcon.vue';
import MpInput from './base/MpInput.vue';
import MpNumberInput from './base/MpNumberInput.vue';

export default {
	name: 'MpFormNumberInput',
	inheritAttrs: false,
	components: { MpIcon, MpNumberInput, MpCol, MpInput },
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
		info: {
			type: String,
			required: false,
		},
		description: {
			type: String,
			required: false,
		},
		placeholder: {
			type: String,
			required: false,
		},
	},
}
</script>
