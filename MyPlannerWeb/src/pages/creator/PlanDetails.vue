<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<span class="page-subtitle">View stats about your plan or manage your plan details.</span>

	<div class="halved">
		<MpCard title="Stats">
			<MpInlineValue label="Participants" :value="plan.stats.numberOfParticipants" />
			<MpInlineValue :value="plan.stats.completedStepsCount">
				<template #label>
					<span title="There can be a slight difference if not all of the participants are synced with the latest version.">
						Steps completed by others
						<MpIcon icon="info-circle" class="text-primary" />
					</span>
				</template>
			</MpInlineValue>
			<div class="mt-3">
				<MpButton v-if="!showSearchBar" @click="showSearchBar = true" icon="search" link>
					Find someone's progress
				</MpButton>
				<div v-else>
					<MpSearch
						id="search-plan-participant"
						v-model="searchTerm"
						placeholder="Name or email"
						:disabled="!searchTerm"
						:busy="searching"
						@search="findParticipants"
					/>
					<div class="d-flex">
						<MpButton v-if="foundParticipantProgress.length" link icon="trash-fill" @click="clearSearch">
							Clear search
						</MpButton>
						<MpButton @click="showSearchBar = false" link class="ms-auto me-2" icon="arrow-up-left">
							Close
						</MpButton>
					</div>
					<ul v-if="foundParticipantProgress.length">
						<li v-for="{ progressId, participant } in foundParticipantProgress" :key="progressId">
							<MpLink :to="$route.fullPath + '/progress/' + progressId">{{ participant.name }}</MpLink>
						</li>
					</ul>
				</div>
			</div>
		</MpCard>
		<MpCard title="Details">
			<MpInlineValue label="Last modified" :value="$date(plan.lastModifiedAt)" />
			<MpInlineValue label="Created" :value="$date(plan.createdAt)" />
			<MpInlineValue label="Number of steps" :value="plan.steps.length" />

			<template #actions>
				<MpLink :to="'/creator/details/' + plan.id + '/edit'">Edit</MpLink>
			</template>
		</MpCard>
	</div>
</template>

<script setup>
import api from '@/api/index.js';
import Plan from '@/models/Plan.js';
import { useSessionStorage } from '@/utils/localStorage.js';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()
const planId = useRoute().params.id;

const plan = ref(new Plan());

const showSearchBar = ref(false);
const searching = ref(false);
const searchTerm = ref();
const searchTermStorage = useSessionStorage('searchTerm/' + planId);
const participantsProgress = ref();
const foundParticipantProgress = ref([]);

onMounted(async () => {
	const planResponse = await api.get('/plans/created/' + planId);
	if (!planResponse)
		router.push('/creator');
	plan.value = new Plan(planResponse);
	if (searchTermStorage.value) {
		searchTerm.value = searchTermStorage.value;
		await findParticipants();
		showSearchBar.value = true;
	}
})

const findParticipants = async () => {
	if (!searchTerm.value || searchTerm.value.length <= 2) {
		foundParticipantProgress.value = [];
		return;
	}
	searching.value = true;
	searchTermStorage.value = searchTerm.value;
	if (participantsProgress.value == null)
		await fetchParticipantsProgress();
	foundParticipantProgress.value = participantsProgress.value
			.filter(p => {
				return p.participant.email.toLowerCase().includes(searchTerm.value)
						|| p.participant.name.toLowerCase().includes(searchTerm.value)
			});
	searching.value = false;
}

const fetchParticipantsProgress = async () => {
	participantsProgress.value = await api.get('/plans/created/' + planId + '/participants');
}

const clearSearch = () => {
	searchTerm.value = undefined;
	foundParticipantProgress.value = [];
	searchTermStorage.remove()
}
</script>
