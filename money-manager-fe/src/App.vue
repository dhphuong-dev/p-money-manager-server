<template>
  <n-config-provider :theme-overrides="themeOverrides" :theme="theme" :breakpoints="breakpoints">
    <router-view></router-view>
  </n-config-provider>
</template>

<script setup lang="ts">
import type { GlobalThemeOverrides } from 'naive-ui';
import { darkTheme, lightTheme } from 'naive-ui';
import { useSettingStore } from '@/stores/setting';
const themeOverrides: GlobalThemeOverrides = {
  common: {}
};

const settingStore = useSettingStore();
const theme = computed(() => (settingStore.darkMode ? darkTheme : lightTheme));

const breakpoints: { [k: string]: number } = {
  xs: 0,
  s: 768,
  m: 1024,
  l: 1280,
  xl: 1536,
  xxl: 1920
};
</script>
