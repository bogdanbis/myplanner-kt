<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<div class="d-flex">
		<h2>{{ plan.title }} </h2>
	</div>
	<span class="page-subtitle" :style="{ '--primary': plan.color }">
		View stats about your plan or manage your plan details.
		<PinPlanButton :plan="plan" />
	</span>

	<div class="halved" :style="{ '--primary': plan.color }">
		<MpCard title="Stats" class="d-flex-column">
			<MpInlineValue label="Participants" :value="plan.stats.numberOfParticipants" />
			<MpInlineValue :value="plan.stats.completedStepsCount">
				<template #label>
					<span title="There can be a slight difference if not all of the participants are synced with the latest version.">
						Steps completed
						<MpIcon icon="info-circle" class="text-primary ml-xs" />
					</span>
				</template>
			</MpInlineValue>
			<div class="mt-l">
				<InviteParticipantButton :plan="plan" />
				<MpLink :to="`/creator/manage/${plan.id}/sent-invites`" class="my-s">
					View sent invites
				</MpLink>
				<MpLink :to="`/creator/manage/${plan.id}/participants`" class="">
					View participants
				</MpLink>
			</div>
		</MpCard>
		<MpCard title="Details">
			<MpInlineValue label="Last modified" :value="$date(plan.lastModifiedAt)" />
			<MpInlineValue label="Created" :value="$date(plan.createdAt)" />
			<MpInlineValue label="Number of steps" :value="plan.steps.length" />
			<MpInlineValue label="Visibility" :value="plan.isPublic ? 'Public' : 'Private'" />

			<template #actions>
				<MpLink :to="`/creator/manage/${plan.id}/edit`">Edit</MpLink>
			</template>
		</MpCard>
	</div>
</template>

<script setup>
import InviteParticipantButton from '@/components/plans/manage-plan/InviteParticipantButton.vue';
import PinPlanButton from '@/components/plans/manage-plan/PinPlanButton.vue';
import { useManagePlanStore } from '@/store/managePlan.js';
import { computed, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();
const planId = computed(() => route.params.id);
const managePlanStore = useManagePlanStore();
const plan = computed(() => managePlanStore.plan);

onMounted(() => fetchPlan())

watch(planId, () => fetchPlan())

const fetchPlan = async () => {
	await managePlanStore.fetchPlan(planId.value);
	if (!managePlanStore.plan)
		return router.push('/creator');
}
</script>
