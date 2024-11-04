<template>
	<div class="mp-form-check" :class="$attrs.class">
		<input
			@change="updateValue"
			@input="updateValue"
			:checked="$attrs.modelValue"
			:disabled="disabled"
			:id="id"
			type="checkbox"
			v-bind="$attrs"
		/>
		<label :for="id">{{ computedLabel }}</label>
	</div>
</template>

<script>
export default {
	name: 'MpCheckbox',
	inheritAttrs: false,

	props: {
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
		required: {
			type: Boolean,
			required: false,
			default: false,
		},
	},

	methods: {
		updateValue(event) {
			this.$emit('update:modelValue', event.target.checked);
		},
	},

	computed: {
		computedLabel() {
			if (this.required)
				return this.label + ' *';
			return this.label;
		},
	},
}
</script>
