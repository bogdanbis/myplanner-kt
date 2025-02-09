<template>
	<circle-progress :class="{ 'd-hidden': !value }" :value :max class="progress-circle" text-format="none" />
</template>

<script setup>
defineProps(['value', 'max']);
</script>
