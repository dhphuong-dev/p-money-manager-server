<script lang="ts" setup>
import { IconPlus, IconCategory2, IconChartPie, IconWallet, IconUser } from '@tabler/icons-vue';
</script>

<template>
  <footer>
    <div class="flex">
      <router-link :to="{ name: 'Home', params: {} }">
        <n-icon>
          <icon-category2 stroke-width="1" />
        </n-icon>
      </router-link>
      <router-link :to="{ name: 'Report', params: {} }">
        <n-icon>
          <icon-chart-pie stroke-width="1" />
        </n-icon>
      </router-link>
      <router-link :to="{ name: 'Wallet', params: {} }">
        <n-icon>
          <icon-wallet stroke-width="1" />
        </n-icon>
      </router-link>
      <router-link :to="{ name: 'Profile', params: {} }">
        <n-icon>
          <icon-user stroke-width="1" />
        </n-icon>
      </router-link>
    </div>
    <router-link class="circle" :to="{ name: 'NewTransaction', params: {} }">
      <n-icon>
        <icon-plus stroke-width="1" />
      </n-icon>
    </router-link>
  </footer>
</template>

<style lang="scss" scoped>
footer {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  background: white;
  .flex {
    display: flex;
    justify-content: space-around;
    align-items: center;
    a {
      padding: 1.6rem;
      display: flex;
      justify-content: center;
      align-items: center;
      &:nth-child(2) {
        margin-right: 10%;
      }
      &:nth-child(3) {
        margin-left: 10%;
      }
      i {
        color: $dark;
        font-size: 3.2rem;
        line-height: 3.2rem;
        transition-property: all;
        transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
        transition-duration: 150ms;
      }
      &.router-link-exact-active i {
        color: $primary;
        transform: translateY(-20%);
      }
    }
  }

  .circle {
    position: absolute;
    left: 50%;
    top: 0;
    transform: translate(-50%, -50%);
    font-size: 2.8rem;
    background-color: $primary;
    border-radius: 50%;
    height: 5rem;
    width: 5rem;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
