<template>
	<div class="nav-top">
		<div @click="emit('show-sidebar')" class="menu-button">
			<span><MpIcon icon="list" /></span>
		</div>
		<div class="logo-container">
			<b class="logo">{{ APP_TITLE }}</b>
		</div>
	</div>
</template>

<script setup>
import { APP_TITLE } from '@/router.js';

const emit = defineEmits(['show-sidebar']);
</script>
