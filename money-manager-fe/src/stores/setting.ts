import { DARK_MODE } from '@enums/auth';

interface ISetting {
  darkMode: boolean;
}

export const useSettingStore = defineStore('settingStore', {
  state: (): ISetting => {
    return {
      darkMode: JSON.parse(localStorage.getItem(DARK_MODE) || 'false')
    };
  },
  actions: {
    changeTheme() {
      localStorage.setItem(DARK_MODE, JSON.stringify(!this.darkMode));
      this.darkMode = !this.darkMode;
    }
  }
});
