<template>
	<MpBackLink to="/">Explore</MpBackLink>
	<h2>{{ plan.title }}</h2>
	<h5 class="page-subtitle">
		<MpIcon icon="person-fill" />
		{{ plan.author.name }}
	</h5>
	<p class="fw-600">{{ plan.shortDescription }}</p>

	<MpButton
		v-if="!plan.acquired"
		@click="acquirePlan"
		:busy="loading"
		class="mb-m"
	>
		Get
	</MpButton>
	<MpCard>
		<PlanImages
			:plan="plan"
			class="mb-m"
		/>
		<p>
			<MpMultilineText :text="plan.description" />
			<br />
			<span class="text-primary">Get this plan to view the entire description.</span>
		</p>
		<MpFormSection
			title="Steps"
			class="steps-form"
			v-auto-animate
		>
			<StepPublicInfo v-for="step in plan.steps" :step="step" />
		</MpFormSection>
		<div v-if="plan.totalSteps > 2">
			<h6 class="m-0">{{ plan.totalSteps - 1 }} more steps available for participants.</h6>
			<div class="mt-m">
				<span class="text-primary">Get this plan to view the rest of the steps and keep track of your
					progress.</span>
				<MpButton
					v-if="!plan.acquired"
					@click="acquirePlan"
					:busy="loading"
					class="mb-m"
				>
					Get
				</MpButton>
			</div>
		</div>
	</MpCard>
</template>

<script setup>
import PlanImages from '@/components/plans/PlanImages.vue';
import StepPublicInfo from '@/components/plans/StepPublicInfo.vue';
import Plan from '@/models/Plan.js';
import { useAuthStore } from '@/store/auth.js';
import { usePlansStore } from '@/store/publicPlans.js';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const authStore = useAuthStore();
const plansStore = usePlansStore();

const router = useRouter();
const planId = useRoute().params.id;
const plan = ref(new Plan());
const loading = ref(true);

const user = computed(() => authStore.user);

onMounted(async () => {
	if (plansStore.publicPlans == null)
		await plansStore.fetchPublicPlans();
	plan.value = plansStore.publicPlans.find(it => it.id === planId);
	if (!plan.value)
		return router.replace('/');
	if (plan.value.acquired)
		return router.replace('/my-plans/' + plan.value.acquired.id);
	loading.value = false;
})

const acquirePlan = async () => {
	if (!user.value)
		return authStore.requestLogin();
	loading.value = true;
	const planProgress = await user.value.acquirePlan(plan.value);
	return router.push('/my-plans/' + planProgress.id);
}
</script>
