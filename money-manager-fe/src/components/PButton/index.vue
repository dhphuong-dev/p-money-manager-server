<script setup lang="ts">
import { NButton } from 'naive-ui';
</script>

<template>
  <n-button
    :bordered="false"
    :block="true"
    icon-placement="left"
    size="large"
    text-color="white"
    :builtin-theme-overrides="{
      borderRadiusLarge: '5px',
      fontWeight: 'bold',
      paddingLarge: '2rem 0'
    }"
    class="btn"
  >
    <slot></slot>
  </n-button>
</template>

<style scoped lang="scss">
.btn {
  font-size: 3rem !important;
}
</style>
