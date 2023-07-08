import type { FormItemRule } from 'naive-ui';
export const passwordValidator = (rule: FormItemRule, value: string) => {
  if (!value) {
    return new Error('Please enter your password');
  } else if (!/\d/.test(value)) {
    return new Error('Password must have at least one number');
  } else if (!/[a-zA-Z]/.test(value)) {
    return new Error('Password must have at least one letter');
  } else if (value.includes(' ')) {
    return new Error('Passwords without spaces');
  } else if (value.length < 8) {
    return new Error('Password must be at least 8 characters');
  }
  return true;
};

export const emaildValidator = (rule: FormItemRule, value: string) => {
  if (!value) {
    return new Error('Please enter your email');
  } else if (!/^[a-z0-9](\.?[a-z0-9]){5,}@g(oogle)?mail\.com$/.test(value)) {
    return new Error('Invalid email format (only gmail accepted)');
  }
  return true;
};
