<template>
	<form class="mp-search" @submit.prevent="$emit('search')">
		<MpInput
			:id
			:label
			:required
			:unlabeled
			type="search"
			v-bind="$attrs"
		/>
		<MpButton :disabled icon="search" type="submit" :busy />
	</form>
</template>

<script>
import MpInput from './base/MpInput.vue';

export default {
	name: 'MpSearch',
	components: { MpInput },
	inheritAttrs: false,

	props: {
		busy: {
			type: Boolean,
			required: false,
			default: false,
		},
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
			required: false,
		},
		required: {
			type: Boolean,
			required: false,
			default: false,
		},
		unlabeled: {
			type: Boolean,
			required: false,
			default: false,
		},
	},

	emits: ['search'],
}
</script>
