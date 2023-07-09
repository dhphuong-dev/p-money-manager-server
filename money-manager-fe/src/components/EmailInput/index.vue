<script setup lang="ts">
import { ref, computed } from 'vue';
const value = ref<string>('');

const options = computed(() => {
  return ['@gmail.com'].map((suffix) => {
    const prefix = value.value.split('@')[0];
    return {
      label: prefix + suffix,
      value: prefix + suffix
    };
  });
});
</script>

<template>
  <n-auto-complete
    v-model:value="value"
    :options="options"
    size="large"
    placeholder="example@gmail.com"
  />
</template>
