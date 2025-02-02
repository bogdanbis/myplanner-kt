<template>
	<MpCol :cols="cols" :cols-sm="colsSm" class="mp-form-item-container">
		<MpInput
			:id="id"
			:label="label"
			:large="large"
			:monospaced="monospaced"
			:placeholder="placeholder"
			:required="required"
			:type="type"
			:unlabeled="unlabeled"
			v-bind="$attrs"
		/>
		<small v-if="info">
			<MpIcon icon="info-circle" class="mr-2" />
			{{ info }}</small>
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
		cols: {
			type: [String, Number],
			required: false,
		},
		colsSm: {
			type: [String, Number],
			required: false,
		},
		description: {
			type: String,
			required: false,
		},
		id: {
			type: String,
			required: true,
		},
		info: {
			type: String,
			required: false,
		},
		label: {
			type: String,
			required: false,
		},
		large: {
			type: Boolean,
			required: false,
			default: false,
		},
		monospaced: {
			type: Boolean,
			required: false,
			default: false,
		},
		placeholder: {
			type: String,
			required: false,
		},
		required: {
			type: Boolean,
			required: false,
			default: false,
		},
		type: {
			type: String,
			required: false,
			default: 'text',
		},
		unlabeled: {
			type: Boolean,
			required: false,
			default: false,
		},
	},
}
</script>
