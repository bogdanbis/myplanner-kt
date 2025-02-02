import { useLocalStorage } from '@/utils/localStorage.js';
import { onMounted, ref } from 'vue';

export function useCollapse(collapsible, startCollapsed) {
	const localStorage = useLocalStorage('collapsed-sections');

	const collapsed = ref(false);

	const toggleCollapsed = () => {
		if (!collapsible) return;
		collapsed.value = !collapsed.value;
		if (typeof startCollapsed === 'string') {
			localStorage.value = { ...localStorage.value, [startCollapsed]: collapsed.value };
		}
	}

	onMounted(() => {
		if (typeof startCollapsed === 'string' && localStorage.value && localStorage.value[startCollapsed] != null)
			collapsed.value = localStorage.value[startCollapsed];
	})

	return { collapsed, toggleCollapsed };
}
