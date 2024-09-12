<template>
	<label v-if="label" :for="id" class="mp-label">{{ computedLabel }}</label>
	<textarea
		v-model="value"
		:id="id"
		class="mp-form-item mp-textarea"
		ref="textarea"
		v-bind="$attrs"
	/>
	<small v-if="showCount" class="mp-textarea-counter">
		{{ modelValue?.length }}
	</small>
</template>

<script>
export default {
	name: 'MpTextarea',
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
		showCount: {
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

	mounted() {
		this.resize()
	},

	methods: {
		resize() {
			const { textarea } = this.$refs;
			textarea.style.height = '62px';
			textarea.style.height = textarea.scrollHeight + 'px';
		},
	},

	watch: {
		modelValue() {
			this.resize()
		},
	},
}
</script>
