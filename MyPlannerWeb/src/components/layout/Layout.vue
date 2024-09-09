<template>
	<div class="page-grid">
		<TopBar @show-sidebar="showSidebar = true" />
		<div class="content">
			<div :class="{focused: showSidebar}" class="sidebar-container">
				<Sidebar @hide-sidebar="showSidebar = false" />
			</div>
			<div class="main-content" @click="showSidebar = false">
				<router-view></router-view>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref } from 'vue';
import Sidebar from './Sidebar.vue';
import TopBar from './TopBar.vue';

const showSidebar = ref(false);
</script>
