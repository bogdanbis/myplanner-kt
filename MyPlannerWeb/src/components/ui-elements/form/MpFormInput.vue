<template>
	<MpCol :cols="cols" :cols-sm="colsSm" class="mp-form-item-container">
		<MpInput
			:id="id"
			:label="label"
			:monospaced="monospaced"
			:placeholder="placeholder"
			:required="required"
			:type="type"
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

export default {
	name: 'MpFormInput',
	inheritAttrs: false,
	components: { MpIcon, MpCol, MpInput },
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
		monospaced: {
			type: Boolean,
			required: false,
			default: false,
		},
		description: {
			type: String,
			required: false,
		},
		info: {
			type: String,
			required: false,
		},
		placeholder: {
			type: String,
			required: false,
		},
		type: {
			type: String,
			required: false,
			default: 'text',
		},
		required: {
			type: Boolean,
			required: false,
			default: false,
		},
	},
}
</script>
