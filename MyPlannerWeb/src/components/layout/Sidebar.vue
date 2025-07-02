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
import { useAuthStore } from '@/store/auth.js';
import { useUiStore } from '@/store/ui.js';
import { computed, onMounted } from 'vue';
import NavMenu from './NavMenu.vue';

const emit = defineEmits(['hide-sidebar']);

const hideSidebar = () => {
	emit('hide-sidebar')
}

const authStore = useAuthStore();

onMounted(() => {
	if (!authStore.user?.receivedInvites)
		authStore.fetchReceivedInvites();
})

const mainMenuItems = computed(() => {
	const items = [
		{
			icon: 'search',
			label: 'Explore',
			path: '/',
		},
		{
			icon: 'bullseye',
			label: 'My Plans',
			path: '/my-plans',
			notificationCount: authStore.user?.pendingInvites?.length,
		},
	];
	if (authStore.user)
		items.push({
			icon: 'envelope-fill',
			label: 'Received Invites',
			path: '/received-invites',
		});
	return items;
})

const uiStore = useUiStore();
uiStore.setPinnedPlans(useAuthStore().user?.uiPreferences.pinnedPlans)

const creatorMenuItems = computed(() => {
	return [
		{
			icon: 'pencil-square',
			label: 'Created Plans',
			path: '/creator',
		},
		{
			icon: 'send',
			label: 'Sent Invites',
			path: '/sent-invites',
		},
		...uiStore.pinnedPlans?.map((plan) => ({
			icon: 'pin-fill',
			label: plan.title,
			path: `/creator/manage/${plan.id}`,
		})),
	];
});
</script>
