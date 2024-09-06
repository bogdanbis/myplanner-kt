<template>
	<label v-if="label" :for="id" class="mp-label">{{ computedLabel }}</label>
	<input
		@focusin="changeToNumber"
		@focusout="changeToText"
		@input="onInput"
		:disabled="disabled"
		:id="id"
		:type="type"
		:value="formattedValue"
		class="mp-form-item"
		inputmode="decimal"
	/>
</template>

<script>
export default {
	name: 'MpNumberInput',
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
		disabled: {
			type: Boolean,
			required: false,
			default: false,
		},
		required: {
			type: Boolean,
			required: false,
			default: false,
		},
	},

	data() {
		return {
			type: 'text',
		}
	},

	computed: {
		formattedValue() {
			if (!this.modelValue && this.modelValue !== 0)
				return null;
			if (this.type === 'number' || typeof this.modelValue !== 'number')
				return this.modelValue;
			return this.$n(this.modelValue);
		},
		computedLabel() {
			if (this.required)
				return this.label + ' *';
			return this.label;
		},
	},

	methods: {
		onInput(e) {
			this.$emit('update:modelValue', e.target.value)
		},
		changeToNumber() {
			this.type = 'number'
		},
		changeToText() {
			this.type = 'text'
			if (!this.modelValue && this.modelValue !== 0)
				this.$emit('update:modelValue', null);
			else {
				let toEmit = this.modelValue;
				if (typeof this.modelValue === 'string')
					toEmit = this.modelValue.replace(',', '.');
				this.$emit('update:modelValue', Number(toEmit));
			}
		},
	},
}
</script>
