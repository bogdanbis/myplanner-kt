<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>Edit Plan</h2>
	<span class="page-subtitle">Make changes to your Plan. You can make these anytime you want.</span>
	<MpCard>
		<PlanForm :plan="planEdits" @submit="updatePlan">
			<template #actions>
				<MpButton v-show="hasChanges" @click="cancelChanges" class="secondary" :disabled="!hasChanges">
					Cancel
				</MpButton>
				<MpButton type="submit" :disabled="!hasChanges || !hasRequiredFields">
					Save
				</MpButton>
			</template>
		</PlanForm>
	</MpCard>
</template>

<script setup>
import api from '@/api';
import { useAuthStore } from '@/store/auth.js';
import { isEqual } from 'lodash';
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import PlanForm from '../../components/plans/PlanForm.vue';

const planId = useRoute().params.id;

const authStore = useAuthStore();
const user = computed(() => authStore.user);
const plan = ref({});
const planEdits = ref({
	title: '',
	description: '',
	shortDescription: '',
	isPublic: '',
	color: '#5856D6',
});

onMounted(async () => {
	if (user.value?.createdPlans == null)
		await user.value?.fetchCreatedPlans();
	plan.value = user.value.createdPlans.find(p => p.id === planId);
	planEdits.value = { ...plan.value };
})

const hasChanges = computed(() => !isEqual(plan.value, planEdits.value))

const hasRequiredFields = computed(() => {
	return planEdits.value.title && planEdits.value.description && planEdits.value.shortDescription
			&& planEdits.value.isPublic != null && planEdits.value.color;
})

const updatePlan = async () => {
	if (!hasChanges.value || !hasRequiredFields.value)
		return;
	await api.put('/plans/' + planId, planEdits.value);
	plan.value = { ...planEdits.value };
}

const cancelChanges = () => {
	planEdits.value = { ...plan.value };
}
</script>
