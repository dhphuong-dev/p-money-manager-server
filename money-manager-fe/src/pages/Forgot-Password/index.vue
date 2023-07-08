<script setup lang="ts">
import type { FormInst, FormItemRule } from 'naive-ui';

import { emaildValidator } from '@/utils/validator/index';
import { useAuthStore } from '@stores/auth';

const router = useRouter();
const { resetPassword } = useAuthStore();

const formInstRef = ref<FormInst | null>(null);
const model = ref({ email: '' });
const loading = ref<boolean>(false);
const message = useMessage();

const rules = {
  email: {
    required: true,
    validator: (rule: FormItemRule, value: string) => emaildValidator(rule, value),
    trigger: 'blur'
  }
};

const loginHandler = async () => {
  formInstRef.value?.validate(async (errors) => {
    if (!errors) {
      loading.value = true;
      try {
        const response = await resetPassword(model.value.email);
        router.push({ name: 'Login', params: {} });
        message.success(response.data.message);
      } catch (error: any) {
        message.error(error.message);
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>

<template>
  <n-form ref="formInstRef" :rules="rules" :model="model" class="login-form">
    <n-form-item label="Email Address" path="email" class="form-element">
      <n-input
        :bordered="false"
        type="text"
        size="large"
        placeholder="example@gmail.com"
        v-model:value="model.email"
      />
    </n-form-item>

    <p-button @click="loginHandler" :loading="loading">Send me a new password</p-button>

    <div class="footer">
      <span>Do you remember the password ? </span>
      <router-link :to="{ name: 'Login', params: {} }" class="footer-link">Login now.</router-link>
    </div>
  </n-form>
</template>

<style scoped lang="scss">
.login-form {
  button {
    margin: 1rem 0;
  }
  .footer {
    text-align: center;
    .footer-link {
      color: $primary;
    }
  }
}
</style>

<route lang="yaml">
name: ForgotPassword
meta:
  layout: auth
</route>
