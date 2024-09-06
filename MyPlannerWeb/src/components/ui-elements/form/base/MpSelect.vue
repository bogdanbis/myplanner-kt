<template>
	<label v-if="label" :for="id" class="mp-label">{{ computedLabel }}</label>
	<select
		v-model="value"
		:id="id"
		class="mp-form-item mp-select"
		v-bind="$attrs"
	>
		<option
			v-for="(option, index) in options"
			:key="index"
			:value="option.value"
			:disabled="option.disabled"
		>{{ option.label }}</option>
	</select>
</template>

<script>
export default {
	name: 'MpSelect',
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
		required: {
			type: Boolean,
			required: false,
			default: false,
		},
		options: {
			type: Array,
			required: true,
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
