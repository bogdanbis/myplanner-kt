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
		<!--<div class="my-account-item">-->
		<!--	<NavMenuItem-->
		<!--		@click="hideSidebar"-->
		<!--		:active="false"-->
		<!--		icon="gear-fill"-->
		<!--		label="Contul meu"-->
		<!--		to="/my-account"-->
		<!--	/>-->
		<!--</div>-->
	</div>
</template>

<script>
import NavMenu from './NavMenu.vue';
import NavMenuItem from './NavMenuItem.vue';

export default {
	name: 'Sidebar',
	components: { NavMenu, NavMenuItem },

	methods: {
		hideSidebar() {
			this.$emit('hide-sidebar');
		},
	},
	emits: ['hide-sidebar'],
};
</script>
