<template>
	<div>
		<div v-for="image in plan.images">
			<img :src="image.src" class="mp-image" alt="" />
			<MpLinkButtonWithConfirm @confirm="deleteImage(image)" with-icons>Delete image</MpLinkButtonWithConfirm>
		</div>
		<MpFileUpload v-if="uploadEnabled && plan.images.length === 0" @upload="upload" accept="image/*">
			Image
		</MpFileUpload>
	</div>
</template>

<script setup>
import Plan from '@/models/Plan.js';

const { plan } = defineProps({
	plan: {
		type: Plan,
		required: true,
	},
	uploadEnabled: {
		type: Boolean,
		required: false,
		default: false,
	},
})

const emit = defineEmits(['upload', 'delete'])

const upload = (file) => {
	emit('upload', file)
}

const deleteImage = async (image) => {
	emit('delete', image)
}
</script>
