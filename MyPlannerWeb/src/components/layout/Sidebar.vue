<template>
	<div class="sidebar">
		<div class="menu">
			<div class="menu-title">
				<span>Tracker</span>
				<div @click="hideSidebar" role="button" class="close-menu-button">
					<MpIcon icon="x" />
				</div>
			</div>
			<NavMenu :menu-items="mainMenuItems" @nav-item-clicked="hideSidebar" />
		</div>
		<div class="menu creator-menu">
			<div class="menu-title">
				<span>Creator</span>
			</div>
			<NavMenu :menu-items="creatorMenuItems" @nav-item-clicked="hideSidebar" />
		</div>
	</div>
</template>

<script setup>
import { useUiStore } from '@/store/ui.js';
import { useLocalStorage } from '@/utils/localStorage.js';
import { computed } from 'vue';
import NavMenu from './NavMenu.vue';

const emit = defineEmits(['hide-sidebar']);

const hideSidebar = () => {
	emit('hide-sidebar')
}

const mainMenuItems = [
	{
		icon: 'search',
		label: 'Explore',
		path: '/',
	},
	{
		icon: 'bullseye',
		label: 'My Plans',
		path: '/my-plans',
	},
]

const uiStore = useUiStore();
const storedPins = useLocalStorage('pinned-plans').value;
if (storedPins)
	uiStore.setPinnedPlans(storedPins)

const creatorMenuItems = computed(() => ([
	{
		icon: 'pencil-square',
		label: 'Created Plans',
		path: '/creator',
	},
	...uiStore.pinnedPlans.map((plan) => ({
		icon: 'pin-fill',
		label: plan.title,
		path: `/creator/manage/${plan.id}`,
	})),
]));
</script>
