<script lang="ts" setup>
interface FooterRoute {
  title: string;
  icon: string;
  router: {
    name: string;
    params: {};
  };
}
const FOOTER_ROUTES: FooterRoute[] = [
  {
    title: 'Home',
    icon: 't2ico t2ico-category',
    router: {
      name: 'Home',
      params: {}
    }
  },
  {
    title: 'Report',
    icon: 't2ico t2ico-chart',
    router: {
      name: 'Report',
      params: {}
    }
  },
  {
    title: 'Budget',
    icon: 't2ico t2ico-presentation',
    router: {
      name: 'Budget',
      params: {}
    }
  },
  {
    title: 'Profile',
    icon: 't2ico t2ico-profile',
    router: {
      name: 'Profile',
      params: {}
    }
  }
];
</script>

<template>
  <footer>
    <div class="flex">
      <router-link v-for="(route, i) in FOOTER_ROUTES" :key="i" :to="route.router">
        <i :class="route.icon"></i>
      </router-link>
    </div>
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
        font-size: 2.6rem;
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
}
</style>
