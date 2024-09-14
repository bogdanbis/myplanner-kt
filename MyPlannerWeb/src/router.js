import PlanDetails from '@/pages/creator/PlanDetails.vue';
import Home from '@/pages/tracker/Home.vue';
import { createRouter, createWebHistory } from 'vue-router';

const MyPlans = () => import('@/pages/tracker/MyPlans.vue');
const AuthoredPlans = () => import('./pages/creator/CreatedPlans.vue');
const CreatePlan = () => import('@/pages/creator/CreatePlan.vue');
const EditPlan = () => import('./pages/creator/EditPlan.vue');

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
			name: 'Plan Details',
			component: PlanDetails,
		},
		{
			path: '/creator/details/:id/edit',
			name: 'Edit Plan',
			component: EditPlan,
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
