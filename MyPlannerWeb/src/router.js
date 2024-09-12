import Home from '@/pages/Home.vue';
import { createRouter, createWebHistory } from 'vue-router';

const MyPlans = () => import('@/pages/MyPlans.vue');
const AuthoredPlans = () => import('@/pages/AuthoredPlans.vue');
const CreatePlan = () => import('@/pages/CreatePlan.vue');

const router = createRouter({
	history: createWebHistory(),
	scrollBehavior() {
		return { y: 0, behavior: 'smooth' }
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
