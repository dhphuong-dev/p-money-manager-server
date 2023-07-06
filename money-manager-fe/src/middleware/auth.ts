import type { RouteLocationNormalized, NavigationGuardNext } from 'vue-router';
import { useAuthStore } from '@stores/auth';

export const auth = (
  from: RouteLocationNormalized,
  to: RouteLocationNormalized,
  next: NavigationGuardNext
) => {
  const { acccesToken, loggedIn } = useAuthStore();
  if (from.meta.requiresAuth && !loggedIn) {
    if (acccesToken) {
      next();
    } else {
      next(`/login?redirect=${to.path}`);
    }
  } else {
    next();
  }
};
