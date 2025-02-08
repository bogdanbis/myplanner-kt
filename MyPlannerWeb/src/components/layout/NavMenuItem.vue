<template>
	<router-link
		:class="{ active: active }"
		:to="to"
		class="menu-item"
		v-bind="$attrs"
	>
		<div class="text-container">
			<MpIcon :icon="icon || 'file-text-fill'"></MpIcon>
			{{ label }}
			<div v-if="notificationCount" class="menu-item-notification-badge">{{ notificationCount }}</div>
		</div>
	</router-link>
</template>

<script>
export default {
	name: 'NavMenuItem',
	props: {
		to: {
			type: String,
			required: true,
		},
		icon: {
			type: String,
			required: false,
		},
		label: {
			type: String,
			required: true,
		},
		notificationCount: {
			type: Number,
			required: false,
		},
		active: {
			type: Boolean,
			required: true,
		},
	},

}
</script>
