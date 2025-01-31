<template>
	<div class="d-block">
		<input
			@change="setFile"
			:accept
			:disabled
			class="d-none"
			ref="input"
			type="file"
			v-bind="$attrs"
		>
		<MpButton @click="chooseFile" :busy :disabled :icon>
			<slot></slot>
		</MpButton>
	</div>
</template>

<script setup>
import { useTemplateRef } from 'vue';
import MpButton from './buttons/MpButton.vue';

defineOptions({
	inheritAttrs: false,
});

defineProps({
	icon: {
		type: String,
		required: false,
		default: 'upload',
	},
	accept: {
		type: String,
		required: false,
		default: null,
	},
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
});

const emit = defineEmits(['upload']);

const input = useTemplateRef('input');

const chooseFile = () => {
	input.value.click();
}

const setFile = (event) => {
	if (event.target.files.length > 0) {
		emit('upload', event.target.files[0])
	}
}
</script>
