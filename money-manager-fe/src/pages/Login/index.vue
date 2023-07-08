<script setup lang="ts">
import type { FormInst, FormItemRule } from 'naive-ui';

import { passwordValidator, emaildValidator } from '@/utils/validator/index';
import type { ILoginBody } from '@/types/auth.types';

const formInstRef = ref<FormInst | null>(null);
const model = ref<ILoginBody>({ email: '', password: '' });

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

const loginHandler = () => {
  console.log('login');
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

    <p-button @click="loginHandler">Login</p-button>

    <div class="footer">
      <span>I'm a new user. </span>
      <router-link :to="{ name: 'Register', params: {} }" class="register-link"
        >Register</router-link
      >
    </div>
  </n-form>
</template>

<style scoped lang="scss">
.login-form {
  .footer {
    margin-top: 2rem;
    text-align: center;
    .register-link {
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
