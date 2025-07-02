<template>
	<MpLink :to="to" class="cursor-pointer mt-l">
		<MpIcon :icon="icon" />
		<slot>{{ routeName }}</slot>
	</MpLink>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';

const props = defineProps({
	to: {
		type: String,
		required: true,
	},
	routeName: {
		type: String,
	},
	icon: {
		type: String,
		required: false,
		default: 'chevron-left',
	},
})

const router = useRouter();

const to = computed(() => {
	if (router.from?.name)
		return router.from.path;
	else
		return props.to;
});

const routeName = computed(() => {
	if (router.from?.name)
		return router.from.name;
	else
		return props.routeName;
});
</script>
