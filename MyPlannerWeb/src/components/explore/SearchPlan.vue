<template>
	<div>
		<MpSearch
			id="plan-title"
			placeholder="Search title"
			v-model="searchTitle"
			@search="searchPlan"
			@clear-search="clearSearch"
			:busy="searching"
			class="search-plan-title"
		/>
		<PlansList v-if="foundPlans.length" :plans="foundPlans" />
	</div>
</template>

<script setup>
import api from '@/api/index.js';
import PlansList from '@/components/explore/PlansList.vue';
import { ref } from 'vue';

const emits = defineEmits(['search', 'clear-search'])

const searchTitle = ref('');
const searching = ref(false);
const foundPlans = ref([]);

const searchPlan = async () => {
	searching.value = true;
	if (!searchTitle) return;
	foundPlans.value = await api.get('/plans/search', { params: { title: searchTitle.value } });
	searching.value = false;
	emits('search');
}

const clearSearch = () => {
	searchTitle.value = '';
	foundPlans.value = [];
	emits('clear-search');
}
</script>
