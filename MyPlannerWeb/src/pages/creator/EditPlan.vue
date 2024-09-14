<template>
	<MpBackLink :to="'/creator/details/' + plan.id">{{ plan.title }}</MpBackLink>
	<h2>Edit Plan</h2>
	<span class="page-subtitle">Make changes to your Plan. You can make changes anytime you want.</span>
	<MpCard>
		<PlanForm :plan="planEdits" @submit="updatePlan">
			<template #actions>
				<MpLinkButtonWithConfirm
					@confirm="deletePlan"
					class="danger me-auto" with-icons
					:busy="deleting"
				>
					Delete Plan
				</MpLinkButtonWithConfirm>
				<MpButton
					v-show="hasChanges"
					@click="initChanges"
					class="secondary"
					:disabled="!hasChanges || updating"
				>
					Cancel
				</MpButton>
				<MpButton type="submit" :disabled="!hasChanges || !hasRequiredFields" :busy="updating">
					Save
				</MpButton>
			</template>
		</PlanForm>
	</MpCard>
</template>

<script setup>
import api from '@/api';
import PlanForm from '@/components/plans/PlanForm.vue';
import Plan from '@/models/Plan.js';
import { useAuthStore } from '@/store/auth.js';
import { isEqual } from 'lodash';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useToast } from 'vue-toastification';

const router = useRouter()
const planId = useRoute().params.id;

const authStore = useAuthStore();
const user = computed(() => authStore.user);
const plan = ref(new Plan());
const planEdits = ref(new Plan());

onMounted(async () => {
	const planResponse = await api.get('/plans/' + planId);
	if (!planResponse)
		router.push('/creator');
	plan.value = new Plan(planResponse);
	initChanges();
})

const hasChanges = computed(() => !isEqual(plan.value, planEdits.value))

const hasRequiredFields = computed(() => {
	return planEdits.value.title && planEdits.value.description && planEdits.value.shortDescription
			&& planEdits.value.isPublic != null && planEdits.value.color;
})

const updating = ref(false);
const updatePlan = async () => {
	updating.value = true;
	if (!hasChanges.value || !hasRequiredFields.value)
		return;
	await api.put('/plans/' + plan.value.id, planEdits.value);
	plan.value = new Plan(planEdits.value);
	updating.value = false;
	user.value.fetchCreatedPlans();
}

const initChanges = () => {
	planEdits.value = new Plan(plan.value);
}

const deleting = ref(false);
const toast = useToast()
const deletePlan = async () => {
	deleting.value = true;
	await api.delete('/plans/' + planId);
	toast('Plan deleted: ' + plan.value.title);
	user.value.fetchCreatedPlans();
	router.push('/creator');
}
</script>
