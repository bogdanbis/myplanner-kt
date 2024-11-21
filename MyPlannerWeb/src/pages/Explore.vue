<template>
	<h2>Explore</h2>
	<span class="page-subtitle">Discover popular plans shared with the community.</span>
	<SearchPlan class="mb-3" @search="showPublicPlans = false" @clear-search="showPublicPlans = true" />
	<PublicPlans v-if="showPublicPlans" />
</template>

<script setup>
import PublicPlans from '@/components/explore/PublicPlans.vue';
import SearchPlan from '@/components/explore/SearchPlan.vue';
import { ref } from 'vue';

const showPublicPlans = ref(true);
</script>
