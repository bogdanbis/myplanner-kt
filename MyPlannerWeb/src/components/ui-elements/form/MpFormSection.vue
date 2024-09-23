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
		<slot v-if="!collapsed"></slot>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue';

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
		type: Boolean,
		required: false,
		default: false,
	},
	title: {
		type: String,
		required: false,
	},
})

const collapsed = ref(startCollapsed);

const toggleCollapsed = () => {
	if (!collapsible) return;
	collapsed.value = !collapsed.value;
}
</script>
