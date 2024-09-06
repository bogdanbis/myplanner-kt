<template>
	<h2 class="page-title">Browse Plans</h2>
	<Card>
		{{ plans }}
	</Card>
</template>

<script setup>
import api from '@/api/index.js';
import { onBeforeMount, ref } from 'vue';
import Card from './ui-elements/Card.vue';

const plans = ref([]);

onBeforeMount(async () => {
	plans.value = await api.get('/plans/browse');
	console.log(plans.value)
})
</script>
