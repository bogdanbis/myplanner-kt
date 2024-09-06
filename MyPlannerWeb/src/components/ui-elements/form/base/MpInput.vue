<template>
	<label v-if="label" :for="id" class="mp-label">{{ computedLabel }}</label>
	<input
		v-model="value"
		:class="{ 'monospaced': monospaced }"
		:id="id"
		:required="required"
		:type="type"
		class="mp-form-item"
		v-bind="$attrs"
	/>
</template>

<script>
export default {
	name: 'MpInput',
	inheritAttrs: false,

	props: {
		modelValue: {},
		id: {
			type: String,
			required: true,
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
	},

	computed: {
		value: {
			get() {
				return this.modelValue
			},
			set(value) {
				this.$emit('update:modelValue', value)
			},
		},
		computedLabel() {
			if (this.required)
				return this.label + ' *';
			return this.label;
		},
	},
}
</script>
