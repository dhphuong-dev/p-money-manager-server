<script setup lang="ts">
import logo from '@/assets/images/auth/logo.png';
</script>

<template>
  <div class="container">
    <div class="auth-layout">
      <div class="auth-head">
        <div>
          <img :src="logo" alt="pMoney Manager Logo" />
        </div>
        <h2>Best way to saving Your Money!</h2>
      </div>
      <router-view />
    </div>
  </div>
</template>

<style scoped lang="scss">
.auth-layout {
  padding: 6rem 0;
  .auth-head {
    padding-bottom: 2rem;
    text-align: center;
    div {
      margin-bottom: 3rem;
    }
    h2 {
      font-size: 2rem;
      line-height: 3.2rem;
      color: $dark;
    }
  }
}
</style>
