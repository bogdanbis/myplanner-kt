<template>
	<MpBackLink :to="'/creator/manage/' + plan.id">Manage</MpBackLink>
	<h2>Edit Plan</h2>
	<span class="page-subtitle">Make changes to your Plan. You can make changes anytime you want.</span>
	<MpCard :style="{ '--primary': planEdits.color }">
		<PlanForm
			:plan="planEdits"
			@upload-image="uploadImage"
			@delete-image="deleteImage"
			:loading
		>
			<template #actions>
				<MpLinkButtonWithConfirm
					@confirm="deletePlan"
					class="danger mr-auto" with-icons
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
				<MpButton @click="updatePlan" :disabled="!hasChanges || !hasRequiredFields" :busy="updating">
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
import { isEqual } from 'lodash';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useToast } from 'vue-toastification';

const router = useRouter();
const planId = useRoute().params.id;

const plan = ref(new Plan());
const planEdits = ref(new Plan());
const loading = ref(true);

onMounted(async () => {
	const planResponse = await api.get('/plans/created/' + planId);
	if (!planResponse)
		router.push('/creator');
	plan.value = new Plan(planResponse);
	initChanges();
	loading.value = false;
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
	const planResponse = await api.put('/plans/' + plan.value.id, planEdits.value);
	plan.value = new Plan(planResponse);
	planEdits.value = new Plan(planResponse);
	updating.value = false;
}

const uploadImage = async (file) => {
	await plan.value.uploadImage(file);
}

const deleteImage = async (image) => {
	await plan.value.deleteImage(image);
	planEdits.value.images = plan.value.images;
}

const initChanges = () => {
	planEdits.value = new Plan(plan.value);
	planEdits.value.images = plan.value.images;
}

const deleting = ref(false);
const toast = useToast()
const deletePlan = async () => {
	deleting.value = true;
	await api.delete('/plans/' + planId);
	toast('Plan deleted: ' + plan.value.title);
	router.push('/creator');
}
</script>
