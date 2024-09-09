import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/pages/Home.vue';
const MyPlans = () => import('@/pages/MyPlans.vue');

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
