<template>
	<MpCol :cols="cols" :cols-sm="colsSm" class="mp-form-item-container" :class="this.class">
		<MpSelect
			:id="id"
			:label="label"
			:options="options"
			:required="required"
			v-bind="$attrs"
		/>
	</MpCol>
</template>

<script>
import MpCol from '../grid/MpCol.vue';
import MpSelect from './base/MpSelect.vue';

export default {
	name: 'MpFormSelect',
	inheritAttrs: false,
	components: { MpSelect, MpCol },
	props: {
		id: {
			type: String,
			required: true,
		},
		class: {
			required: false,
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
		options: {
			type: Array,
			required: true,
		},
		required: {
			type: Boolean,
			required: false,
			default: false,
		},
	},
}
</script>
