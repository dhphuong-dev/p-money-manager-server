<script setup lang="ts">
import type { FormInst, FormItemRule } from 'naive-ui';

import {
  passwordValidator,
  confirmPasswordValidator,
  emaildValidator
} from '@/utils/validator/index';
import type { IRegisterBody } from '@/types/auth.types';
import { useAuth } from '@/composables/useAuth';

const router = useRouter();
const { register } = useAuth();

const formInstRef = ref<FormInst | null>(null);
const model = ref<IRegisterBody>({ fullName: '', email: '', password: '', confirmPassword: '' });
const loading = ref<boolean>(false);
const message = useMessage();

const rules = {
  fullName: {
    required: true,
    message: 'Please enter your full name',
    trigger: 'blur'
  },
  email: {
    required: true,
    validator: (rule: FormItemRule, email: string) => emaildValidator(rule, email),
    trigger: 'blur'
  },
  password: {
    required: true,
    validator: (rule: FormItemRule, password: string) => passwordValidator(rule, password),
    trigger: 'blur'
  },
  rePassword: {
    required: true,
    validator: (rule: FormItemRule, rePassword: string) =>
      confirmPasswordValidator(rule, rePassword, model.value.password),
    trigger: 'blur'
  }
};

const registerHandler = () => {
  formInstRef.value?.validate(async (errors) => {
    if (!errors) {
      loading.value = true;
      try {
        const response = await register(model.value);
        router.push({ name: 'Login', params: {} });
        message.success('Register succesfully');
      } catch (error: any) {
        message.error(error?.message);
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>

<template>
  <n-form ref="formInstRef" :rules="rules" :model="model" class="register-form">
    <n-form-item label="Full Name" path="fullName" class="form-element">
      <n-input
        :bordered="false"
        type="text"
        size="large"
        placeholder="pMoney"
        v-model:value="model.fullName"
      />
    </n-form-item>

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

    <n-form-item label="Confirm Password" path="confirmPassword" class="form-element">
      <n-input
        :bordered="false"
        type="password"
        size="large"
        placeholder="password"
        v-model:value="model.confirmPassword"
      />
    </n-form-item>

    <p-button @click="registerHandler" :loading="loading" attr-type="submit">Register</p-button>

    <div class="footer">
      <span>I'm ready a member. </span>
      <router-link :to="{ name: 'Login', params: {} }" class="register-link">Login</router-link>
    </div>
  </n-form>
</template>

<style scoped lang="scss">
.register-form {
  button {
    margin: 1rem 0;
  }
  .footer {
    text-align: center;
    .register-link {
      color: $primary;
    }
  }
}
</style>

<route lang="yaml">
name: Register
meta:
  layout: auth
</route>
