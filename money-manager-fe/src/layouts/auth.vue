<script setup lang="ts">
import logo from '@/assets/images/auth/logo.png';
</script>

<template>
  <div class="container">
    <div class="auth-head">
      <div>
        <img :src="logo" alt="pMoney Manager Logo" />
      </div>
      <h2>Best way to saving Your Money!</h2>
    </div>
    <router-view />
  </div>
</template>

<style scoped lang="scss">
@use '@styles/variables' as var;
.auth-head {
  padding-top: 6rem;
  padding-bottom: 2rem;
  text-align: center;
  div {
    margin-bottom: 3rem;
  }
  h2 {
    font-size: 2rem;
    line-height: 3.2rem;
    color: var.$dark;
  }
}
</style>
