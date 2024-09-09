<template>
	<div class="sidebar">
		<div class="main-menu">
			<div class="menu-title">
				<span>Menu</span>
				<div @click="hideSidebar" role="button" class="close-menu-button">
					<MpIcon icon="x" />
				</div>
			</div>
			<NavMenu @nav-item-clicked="hideSidebar" />
		</div>
	</div>
</template>

<script setup>
import NavMenu from './NavMenu.vue';

const emit = defineEmits(['hide-sidebar']);

const hideSidebar = () => {
	emit('hide-sidebar')
}
</script>
