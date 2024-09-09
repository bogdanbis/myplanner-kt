<template>
	<div class="nav-menu">
		<NavMenuItem
			v-for="(menuItem, index) in menuItems"
			@click="emit('nav-item-clicked')"
			:active="menuItem.isActive"
			:icon="menuItem.icon"
			:index="index + 2"
			:key="index"
			:label="menuItem.label"
			:to="menuItem.path"
		/>
	</div>
	<div
		v-if="selectedIndex != null"
		:style="itemNegativeIndex"
		class="active-menu-item"
	></div>
</template>

<script setup>
import { computed, ref } from 'vue';
import { useRoute } from 'vue-router';
import NavMenuItem from './NavMenuItem.vue';

const emit = defineEmits(['nav-item-clicked']);


const menu = [
	{
		icon: 'search',
		label: 'Explore',
		path: '/',
	},
	{
		label: 'My Plans',
		path: '/my-plans',
	},
];

const route = useRoute();

const selectedIndex = ref(null);

const menuItems = computed(() => {
	console.log(route.path)
	return menu.map((menuItem, index) => {
		const isActive = route.path === menuItem.path
		if (isActive)
			selectedIndex.value = index
		return {
			...menuItem,
			isActive: isActive,
		}
	})
})

const itemNegativeIndex = computed(() => {
	const negativeIndex = menu.length - selectedIndex.value;
	return { '--item-negative-index': negativeIndex }
});
</script>
