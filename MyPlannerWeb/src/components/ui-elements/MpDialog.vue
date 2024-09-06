<template>
	<dialog class="dialog" ref="dialog" :class="'dialog-size-' + size" @click.self="close">
		<div class="dialog-body">
		<div class="dialog-title">
			{{ title }}
			<button class="dialog-close-btn" @click="close"><MpIcon icon="x-lg" /></button>
		</div>
		<slot></slot>
		</div>
	</dialog>
</template>

<script setup>
import { useTemplateRef } from 'vue';

defineProps({
	size: {
		type: String,
		required: false,
		default: 'normal',
	},
	title: {
		type: String,
		required: false,
	},
})

const dialog = useTemplateRef('dialog');

const open = () => {
	dialog.value.showModal()
};

const close = () => {
	dialog.value.close()
}

defineExpose({ open, close })
</script>
