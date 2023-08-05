<script setup lang="ts">
import {
  IconSettings,
  IconChevronRight,
  IconWallet,
  IconTicket,
  IconLock,
  IconInfoSquareRounded,
  IconLogout
} from '@tabler/icons-vue';

import { getCurrentUser } from '@api/user';
import type { IUser } from '@/types/user.types';
import { useAuth } from '@/composables/useAuth';

const { push } = useRouter();
const { success } = useMessage();
const { logout } = useAuth();

const user = ref<IUser>();
onBeforeMount(async () => {
  try {
    const { data } = await getCurrentUser();
    user.value = data.data;
  } catch (err) {}
});

const logoutHandler = async (): Promise<void> => {
  try {
    const response = await logout();
    success(response.data.message);
    push({ name: 'Login', params: {} });
  } catch (error) {}
};
</script>

<template>
  <div id="profile">
    <p-header title="My Profile">
      <template #function>
        <n-icon :size="32">
          <icon-settings />
        </n-icon>
      </template>
    </p-header>

    <n-space class="user" vertical align="center">
      <n-avatar round :size="72" :src="user?.avatar" />
      <div class="name">{{ user?.fullName }}</div>
      <div class="email">{{ user?.email }}</div>
    </n-space>

    <div class="general">General</div>

    <router-link :to="{ name: 'Home', params: {} }" class="link-item">
      <n-space align="center" justify="space-between">
        <n-space align="center" justify="space-between">
          <n-icon>
            <IconWallet />
          </n-icon>
          <span>My Wallet</span>
        </n-space>
        <n-icon>
          <IconChevronRight />
        </n-icon>
      </n-space>
    </router-link>

    <router-link :to="{ name: 'Home', params: {} }" class="link-item">
      <n-space align="center" justify="space-between">
        <n-space align="center" justify="space-between">
          <n-icon>
            <IconTicket />
          </n-icon>
          <span>Tools</span>
        </n-space>
        <n-icon>
          <IconChevronRight />
        </n-icon>
      </n-space>
    </router-link>

    <router-link :to="{ name: 'Home', params: {} }" class="link-item">
      <n-space align="center" justify="space-between">
        <n-space align="center" justify="space-between">
          <n-icon>
            <IconLock />
          </n-icon>
          <span>Privacy</span>
        </n-space>
        <n-icon>
          <IconChevronRight />
        </n-icon>
      </n-space>
    </router-link>

    <router-link :to="{ name: 'Home', params: {} }" class="link-item">
      <n-space align="center" justify="space-between">
        <n-space align="center" justify="space-between">
          <n-icon>
            <IconInfoSquareRounded />
          </n-icon>
          <span>About</span>
        </n-space>
        <n-icon>
          <IconChevronRight />
        </n-icon>
      </n-space>
    </router-link>

    <div @click="logoutHandler" class="link-item">
      <n-space align="center" justify="space-between">
        <n-space align="center" justify="space-between" class="logout">
          <n-icon>
            <IconLogout />
          </n-icon>
          <span>Logout</span>
        </n-space>
        <n-icon>
          <IconChevronRight />
        </n-icon>
      </n-space>
    </div>
  </div>
</template>

<style lang="scss" scoped>
#profile {
  .user {
    padding: 2rem 0;
    .name {
      font-size: 2.4rem;
      color: $primary;
      font-weight: bold;
    }
    .email {
      color: $gray;
      font-size: 2rem;
    }
  }

  .general {
    color: $primary;
    font-size: 2.2rem;
    font-weight: bold;
    margin-top: 4rem;
  }

  .link-item {
    display: block;
    padding: 1rem 0;
    margin: 1rem 0;
    color: $dark;
    span {
      font-size: 1.8rem;
      font-weight: bold;
    }
    i {
      font-size: 2rem;
    }

    .logout {
      color: $pink;
    }
  }
}
</style>

<route lang="yaml">
name: Profile
meta:
  requiresAuth: true
  layout: main
</route>
