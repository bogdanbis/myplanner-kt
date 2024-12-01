<template>
	<div class="mp-search">
		<form @submit.prevent="search" class="mp-search-form" :class="$attrs.class">
			<MpInput
				:id
				:label
				:placeholder
				:unlabeled
				type="search"
				:modelValue="modelValue"
				@update:modelValue="emit('update:modelValue', $event)"
			/>
			<MpButton :disabled="modelValue?.length < minLength" icon="search" type="submit" :busy />
		</form>
		<MpButton v-if="didSearch" @click="clearSearch" link icon="trash-fill" class="mp-search-clear">
			Clear search
		</MpButton>
	</div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import MpInput from './base/MpInput.vue';

defineOptions({
	inheritAttrs: false,
});

const { modelValue } = defineProps({
	modelValue: {},
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
	minLength: {
		type: [String, Number],
		required: false,
		default: 1,
	},
	placeholder: {
		type: String,
		required: false,
	},
	unlabeled: {
		type: Boolean,
		required: false,
		default: false,
	},
});

const emit = defineEmits(['update:modelValue', 'search', 'clear-search']);

onMounted(() => {
	didSearch.value = Boolean(modelValue)
})

const didSearch = ref(false);

const search = () => {
	if (!modelValue)
		return;
	emit('search');
	didSearch.value = true;
}

const clearSearch = () => {
	didSearch.value = false;
	emit('update:modelValue', '');
	emit('clear-search');
}
</script>
