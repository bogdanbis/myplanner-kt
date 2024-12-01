<template>
	<div>
		<MpSearch
			id="plan-title"
			placeholder="Find by title"
			v-model="searchTitle"
			@search="searchPlan"
			@clear-search="clearSearch"
			:busy="searching"
			min-length="3"
			class="search-plan-title w-50-desktop"
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
