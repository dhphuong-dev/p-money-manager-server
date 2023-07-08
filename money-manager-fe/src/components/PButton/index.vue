<script setup lang="ts">
import { NButton } from 'naive-ui';
</script>

<template>
  <n-button
    :bordered="false"
    :block="true"
    icon-placement="left"
    size="large"
    color="#0012ff"
    text-color="white"
    :builtin-theme-overrides="{
      textColorHover: '',
      textColorFocus: '',
      rippleColor: '',
      borderRadiusLarge: '5px',
      fontWeight: 'bold',
      fontSizeLarge: '5rem',
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
