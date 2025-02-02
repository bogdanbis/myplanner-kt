<template>
	<MpCol cols="1">
		<span
			class="mp-form-section-title"
			:class="{ collapsible: collapsible, collapsed: collapsed, 'smaller-title': smaller }"
			@click="toggleCollapsed"
		>
			<MpIcon v-if="collapsible" icon="chevron-down" />
			<slot />
		</span>
	</MpCol>
</template>

<script setup>
const emit = defineEmits(['collapsed-changed']);

const { collapsible } = defineProps({
	collapsible: {
		type: Boolean,
		required: false,
		default: false,
	},
	collapsed: {
		type: Boolean,
		required: false,
		default: false,
	},
	smaller: {
		type: Boolean,
		required: false,
		default: false,
	},
});

const toggleCollapsed = () => {
	if (collapsible)
		emit('collapsed-changed');
};
</script>
