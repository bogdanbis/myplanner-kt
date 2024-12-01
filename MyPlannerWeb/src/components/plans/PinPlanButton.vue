<template>
	<MpButton @click="togglePin" :icon="isPinned ? 'pin-fill' : 'pin-angle'" link>
		{{ isPinned ? 'Pinned' : 'Pin to sidebar' }}
	</MpButton>
</template>

<script setup>
import Plan from '@/models/Plan.js';
import { useUiStore } from '@/store/ui.js';
import { computed } from 'vue';

const { plan } = defineProps({
	plan: {
		type: Plan,
		required: true,
	},
})

const uiStore = useUiStore();
const pinnedPlans = computed({
	get() {
		return uiStore.pinnedPlans;
	},
	set(value) {
		uiStore.setPinnedPlans(value);
	},
});

const isPinned = computed(() => pinnedPlans.value?.find(p => p.id === plan.id))

const togglePin = () => {
	if (isPinned.value)
		uiStore.unpinPlan(plan);
	else
		uiStore.pinPlan(plan);
}
</script>
