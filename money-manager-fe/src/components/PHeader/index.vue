<script setup lang="ts">
const props = defineProps<{
  title: string;
}>();
</script>

<template>
  <header>
    <n-space align="center" justify="space-between">
      <h1>{{ props.title }}</h1>
      <slot name="function" />
    </n-space>
  </header>
</template>
