import type { FormItemRule } from 'naive-ui';
export const passwordValidator = (rule: FormItemRule, password: string) => {
  if (!password) {
    return new Error('Please enter your password');
  } else if (!/\d/.test(password)) {
    return new Error('Password must have at least one number');
  } else if (!/[a-zA-Z]/.test(password)) {
    return new Error('Password must have at least one letter');
  } else if (password.includes(' ')) {
    return new Error('Passwords without spaces');
  } else if (password.length < 8) {
    return new Error('Password must be at least 8 characters');
  }
  return true;
};

export const rePasswordValidator = (rule: FormItemRule, rePassword: string, password: string) => {
  if (password.trim() === '') {
    return new Error('Please enter your password');
  } else if (password !== rePassword) {
    return new Error('The repeat password must is as same as the password');
  }
  return true;
};

export const emaildValidator = (rule: FormItemRule, email: string) => {
  if (!email) {
    return new Error('Please enter your email');
  } else if (!/^[a-z0-9](\.?[a-z0-9]){5,}@g(oogle)?mail\.com$/.test(email)) {
    return new Error('Invalid email format (only gmail accepted)');
  }
  return true;
};
