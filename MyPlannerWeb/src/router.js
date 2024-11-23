import Explore from '@/pages/Explore.vue';
import { createRouter, createWebHistory } from 'vue-router';

const MyPlans = () => import('@/pages/MyPlans.vue');
const PlanProgress = () => import('@/pages/participant/PlanProgress.vue');
const AuthoredPlans = () => import('./pages/creator/CreatedPlans.vue');
const CreatePlan = () => import('@/pages/creator/CreatePlan.vue');
const ManagePlan = () => import('@/pages/creator/ManagePlan.vue');
const EditPlan = () => import('@/pages/creator/EditPlan.vue');
const PlanParticipants = () => import('@/pages/creator/PlanParticipants.vue');
const ParticipantPlanProgress = () => import('@/pages/creator/ParticipantPlanProgress.vue');

const router = createRouter({
	history: createWebHistory(),
	scrollBehavior() {
		return { top: 0, behavior: 'smooth' }
	},
	routes: [
		{
			path: '/',
			name: 'Explore',
			component: Explore,
		},
		{
			path: '/my-plans',
			name: 'My Plans',
			component: MyPlans,
		},
		{
			path: '/my-plans/:id',
			name: 'Plan Progress',
			component: PlanProgress,
		},
		{
			path: '/creator',
			name: 'Authored Plans',
			component: AuthoredPlans,
		},
		{
			path: '/creator/new',
			name: 'Create a Plan',
			component: CreatePlan,
		},
		{
			path: '/creator/manage/:id',
			name: 'Manage Plan',
			component: ManagePlan,
		},
		{
			path: '/creator/manage/:id/edit',
			name: 'Edit Plan',
			component: EditPlan,
		},
		{
			path: '/creator/manage/:id/participants',
			name: 'Participants progress',
			component: PlanParticipants,
		},
		{
			path: '/creator/manage/:id/participants/:progressId',
			name: 'Participant Plan Progress',
			component: ParticipantPlanProgress,
		},
		{
			path: '/:pathMatch(.*)*',
			redirect: '/',
		},
	],
});

export const APP_TITLE = 'Progress Planner';
router.beforeEach((to) => {
	document.title = to.name ? `${to.name} • ${APP_TITLE}` : APP_TITLE;
});

export default router;
