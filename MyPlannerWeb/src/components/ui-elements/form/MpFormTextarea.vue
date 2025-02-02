<template>
	<MpCol :cols="cols" :cols-sm="colsSm" class="mp-form-item-container">
		<MpTextarea
			:id="id"
			:label="label"
			:placeholder="placeholder"
			:required="required"
			:show-count="showCount"
			:unlabeled="unlabeled"
			v-bind="$attrs"
		/>
		<small v-if="info"><MpIcon icon="info-circle" class="me-2" />{{ info }}</small>
		<small v-if="description">{{ description }}</small>
	</MpCol>
</template>

<script>
import MpCol from '../grid/MpCol.vue';
import MpTextarea from './base/MpTextarea.vue';

export default {
	name: 'MpFormTextarea',
	components: { MpTextarea, MpCol },
	inheritAttrs: false,
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
		description: {
			type: String,
			required: false,
		},
		info: {
			type: String,
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
		showCount: {
			type: Boolean,
			required: false,
			default: false,
		},
		placeholder: {
			type: String,
			required: false,
		},
		unlabeled: {
			type: Boolean,
			required: false,
			default: false,
		},
	},
}
</script>
