<template>
	<div class="mp-form-section">
		<MpFormSectionTitle
			v-if="title"
			@collapsed-changed="toggleCollapsed"
			:collapsible
			:collapsed
			:smaller="smallerTitle"
		>
			{{ title }}
		</MpFormSectionTitle>
		<slot name="non-collapsible"></slot>
		<slot v-if="!collapsed"></slot>
	</div>
</template>

<script setup>
import { useCollapse } from '@/composables/collapse.js';

const { collapsible, startCollapsed } = defineProps({
	collapsible: {
		type: Boolean,
		required: false,
		default: false,
	},
	smallerTitle: {
		type: Boolean,
		required: false,
		default: false,
	},
	startCollapsed: {
		type: [Boolean, String],
		required: false,
		default: false,
	},
	title: {
		type: String,
		required: false,
	},
})

const { collapsed, toggleCollapsed } = useCollapse(collapsible, startCollapsed)
</script>
