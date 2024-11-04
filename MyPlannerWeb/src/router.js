import Home from '@/pages/Home.vue';
import { createRouter, createWebHistory } from 'vue-router';

const MyPlans = () => import('@/pages/MyPlans.vue');
const PlanProgress = () => import('@/pages/participant/PlanProgress.vue');
const AuthoredPlans = () => import('./pages/creator/CreatedPlans.vue');
const CreatePlan = () => import('@/pages/creator/CreatePlan.vue');
const PlanDetails = () => import('@/pages/creator/PlanDetails.vue');
const EditPlan = () => import('@/pages/creator/EditPlan.vue');
const ParticipantPlanProgress = () => import('@/pages/creator/ParticipantPlanProgress.vue');

const router = createRouter({
	history: createWebHistory(),
	scrollBehavior() {
		return { top: 0, behavior: 'smooth' }
	},
	routes: [
		{
			path: '/',
			name: 'Home',
			component: Home,
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
			path: '/creator/details/:id',
			name: 'Manage Plan',
			component: PlanDetails,
		},
		{
			path: '/creator/details/:id/edit',
			name: 'Edit Plan',
			component: EditPlan,
		},
		{
			path: '/creator/details/:id/progress/:progressId',
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
