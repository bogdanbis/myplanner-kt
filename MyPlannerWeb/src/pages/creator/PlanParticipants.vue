<template>
	<MpBackLink :to="`/creator/manage/${plan.id}`">Manage</MpBackLink>
	<h2>{{ plan.title }}</h2>

	<MpCard title="Participants progress">
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
		<MpTable :fields="tableFields" :empty="!displayedParticipants?.length">
			<tr v-for="pp in displayedParticipants">
				<td>
					<MpLink :to="`/creator/manage/${planId}/participants/${pp.id}`">{{ pp.participant.name }}</MpLink>
				</td>
				<td class="d-flex">
					<ProgressCircle :value="pp.completedStepsCount" :max="pp.totalStepsCount" class="m-right-0" />
					{{ pp.completedStepsCount }}/{{ pp.totalStepsCount }}
				</td>
				<td>{{ $date(pp.acquiredAt) }}</td>
				<td>{{ $relativeDate(pp.lastActive) }}</td>
			</tr>
		</MpTable>
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

const tableFields = [
	{ label: 'Name' },
	{ label: 'Completed' },
	{ label: 'Started' },
	{ label: 'Last active' },
];

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
