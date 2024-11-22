<template>
	<circle-progress :value :max class="progress-circle" text-format="none" />
</template>

<script setup>
defineProps(['value', 'max']);
</script>
