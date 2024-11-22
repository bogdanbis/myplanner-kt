<template>
	<MpBackLink :to="`/creator/manage/${plan.id}`">Manage</MpBackLink>
	<h2>{{ plan.title }}</h2>

	<MpSearch
		id="search-plan-participant"
		v-model="searchTerm"
		placeholder="Search by name or email"
		:disabled="!searchTerm"
		:busy="searching"
		@search="findParticipants"
		@clear-search="clearSearch"
		class="w-50-desktop"
	/>
	<MpCard title="Participants progress">
		<ul>
			<li v-for="pp in displayedParticipants">
				<MpLink :to="`/creator/manage/${planId}/participant-progress/${pp.id}`">
					{{ pp.participant.name }}
				</MpLink>
				<div class="d-flex">
					<ProgressCircle :value="pp.completedStepsCount" :max="pp.totalStepsCount" class="m-right-0" />
					<div>Completed {{ pp.completedStepsCount }}/{{ pp.totalStepsCount }}</div>
				</div>
				<div>Started at: {{ $date(pp.acquiredAt) }}</div>
				<div>Last active: {{ $date(pp.lastActive) }}</div>
			</li>
		</ul>
	</MpCard>
</template>

<script setup>
import api from '@/api/index.js';
import Plan from '@/models/Plan.js';
import PlanProgress from '@/models/PlanProgress.js';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const planId = useRoute().params.id;
const plan = ref(new Plan());

const searching = ref(false);
const searchTerm = ref();
const participantsProgress = ref();
const foundParticipantProgress = ref([]);
const showFound = ref(false);

onMounted(async () => {
	const planResponse = await api.get('/plans/created/' + planId);
	if (!planResponse)
		return router.push('/creator');
	plan.value = new Plan(planResponse);
	await fetchParticipantsProgress();
})

const displayedParticipants = computed(() =>
		showFound.value ? foundParticipantProgress.value : participantsProgress.value)

const fetchParticipantsProgress = async () => {
	const response = await api.get('/plans/created/' + planId + '/participants');
	participantsProgress.value = response.map(pp => new PlanProgress(pp));
}

const findParticipants = async () => {
	if (searchTerm.value?.length <= 2) {
		foundParticipantProgress.value = [];
		return;
	}
	searching.value = true;
	foundParticipantProgress.value = participantsProgress.value.filter(p => {
		return p.participant.email.toLowerCase().includes(searchTerm.value)
				|| p.participant.name.toLowerCase().includes(searchTerm.value)
	});
	searching.value = false;
	showFound.value = true;
}

const clearSearch = () => {
	searchTerm.value = undefined;
	foundParticipantProgress.value = [];
	showFound.value = false;
}
</script>
