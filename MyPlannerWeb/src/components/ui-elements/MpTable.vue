<template>
	<div class="mp-table-container">
		<table
			:id="id" class="mp-table"
			:class="{
			'busy': busy,
			'mp-table-fixed-layout': fixedLayout,
		}"
		>
			<thead>
				<slot name="header"></slot>
				<tr>
					<th v-for="field in fields">{{ field.label }}</th>
				</tr>
			</thead>
			<tbody v-if="busy || empty">
				<tr class="placeholder-row">
					<td :colspan="(fixedLayout ? fields?.length : fixedLayout) || 99">
						<LoadingIcon v-if="busy" with-text />
						<span v-else-if="empty">{{ emptyText || 'No records.' }}</span>
					</td>
				</tr>
			</tbody>
			<tbody v-else>
				<slot></slot>
			</tbody>
		</table>
	</div>
</template>

<script setup>
import LoadingIcon from './icons/LoadingIcon.vue';

defineProps({
	busy: {
		type: Boolean,
		required: false,
		default: false,
	},
	empty: {
		type: Boolean,
		required: false,
		default: false,
	},
	emptyText: {
		type: String,
		required: false,
	},
	fields: {
		type: Array,
		required: false,
		default: () => ([]),
	},
	fixedLayout: {
		type: [Boolean, String, Number],
		required: false,
	},
	id: {
		required: false,
	},
	noHover: {
		type: Boolean,
		required: false,
		default: false,
	},
})
</script>
