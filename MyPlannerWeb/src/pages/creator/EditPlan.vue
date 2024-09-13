<template>
	<MpBackLink to="/creator">Created Plans</MpBackLink>
	<h2>Edit Plan</h2>
	<span class="page-subtitle">Make changes to your Plan. You can make these anytime you want.</span>
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
import { useAuthStore } from '@/store/auth.js';
import { isEqual } from 'lodash';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useToast } from 'vue-toastification';

const router = useRouter()
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
	tasks: [],
});

onMounted(async () => {
	plan.value = await api.get('/plans/' + planId);
	if (!plan.value)
		router.push('/creator');
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
	await api.put('/plans/' + planId, planEdits.value);
	plan.value = { ...planEdits.value };
	plan.value.tasks = [...planEdits.value.tasks];
	updating.value = false;
	user.value.fetchCreatedPlans();
}

const initChanges = () => {
	planEdits.value = { ...plan.value };
	planEdits.value.tasks = [...plan.value.tasks];
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
