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
		active: {
			type: Boolean,
			required: true,
		},
	},

}
</script>
