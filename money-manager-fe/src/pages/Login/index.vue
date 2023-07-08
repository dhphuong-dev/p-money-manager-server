<script setup lang="ts">
import type { FormInst, FormItemRule } from 'naive-ui';

import { passwordValidator, emaildValidator } from '@/utils/validator/index';
import type { ILoginBody } from '@/types/auth.types';
import { useAuthStore } from '@stores/auth';

const router = useRouter();
const { login } = useAuthStore();

const formInstRef = ref<FormInst | null>(null);
const model = ref<ILoginBody>({ email: '', password: '' });
const loading = ref<boolean>(false);
const message = useMessage();

const rules = {
  email: {
    required: true,
    validator: (rule: FormItemRule, value: string) => emaildValidator(rule, value),
    trigger: 'blur'
  },
  password: {
    required: true,
    validator: (rule: FormItemRule, value: string) => passwordValidator(rule, value),
    trigger: 'blur'
  }
};

const loginHandler = async () => {
  formInstRef.value?.validate(async (errors) => {
    if (!errors) {
      loading.value = true;
      try {
        const response = await login(model.value);
        setTimeout(() => {
          loading.value = false;
          router.push({ name: 'Home', params: {} });
          message.success('Login succesfully');
        }, 2000);
      } catch (error: any) {
        setTimeout(() => {
          loading.value = false;
          message.error(error?.message);
        }, 2000);
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

    <n-form-item label="Password" path="password" class="form-element">
      <n-input
        :bordered="false"
        type="password"
        size="large"
        placeholder="password"
        v-model:value="model.password"
      />
    </n-form-item>

    <p-button @click="loginHandler" :loading="loading">Login</p-button>

    <div class="footer">
      <router-link :to="{ name: 'ForgotPassword', params: {} }" class="footer-link"
        >Forgot password ?</router-link
      >
    </div>

    <div class="footer">
      <span>Have not you an account ? </span>
      <router-link :to="{ name: 'Register', params: {} }" class="footer-link"
        >Register now.</router-link
      >
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
name: Login
meta:
  layout: auth
</route>
