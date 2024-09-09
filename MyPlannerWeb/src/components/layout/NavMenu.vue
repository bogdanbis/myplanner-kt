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

const props = defineProps({
	menuItems: {
		type: Array,
		required: true,
	},
})

const route = useRoute();

const selectedIndex = ref(null);

const menuItems = computed(() => {
	let activeIndex = null;
	const menuItems = props.menuItems.map((menuItem, index) => {
		let isActive = route.path === menuItem.path;
		if (isActive)
			activeIndex = index;
		return {
			...menuItem,
			isActive: isActive,
		}
	})
	selectedIndex.value = activeIndex;
	return menuItems;
})

const itemNegativeIndex = computed(() => {
	const negativeIndex = props.menuItems.length - selectedIndex.value;
	return { '--item-negative-index': negativeIndex }
});
</script>
