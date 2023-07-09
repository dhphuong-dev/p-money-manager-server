<script setup lang="ts">
import type { FormInst, FormItemRule } from 'naive-ui';

import { passwordValidator, emaildValidator } from '@/utils/validator/index';
import type { ILoginBody } from '@/types/auth.types';
import { useAuth } from '@/composables/useAuth';

const router = useRouter();
const { login } = useAuth();

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

const options = computed(() => {
  return ['@gmail.com'].map((suffix) => {
    const prefix = model.value.email.split('@')[0];
    return {
      label: prefix + suffix,
      value: prefix + suffix
    };
  });
});

const loginHandler = async () => {
  formInstRef.value?.validate(async (errors) => {
    if (!errors) {
      loading.value = true;
      try {
        await login(model.value);
        message.success('Login succesfully');
        router.push({ name: 'Home', params: {} });
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
  <n-form ref="formInstRef" :rules="rules" :model="model" class="login-form">
    <n-form-item label="Email Address" path="email" class="form-element">
      <email-input v-model:value="model.email" />
    </n-form-item>

    <n-form-item label="Password" path="password" class="form-element">
      <n-input
        type="password"
        size="large"
        placeholder="password"
        v-model:value="model.password"
      />
    </n-form-item>

    <p-button @click="loginHandler" :loading="loading" attr-type="submit">Login</p-button>

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
