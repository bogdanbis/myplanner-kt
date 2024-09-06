<template>
	<h1 class="page-title">Browse Plans</h1>
	<Card v-for="plan in plans" :title="plan.title">
		<span>{{ plan.description }}</span>
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
