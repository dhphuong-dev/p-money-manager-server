# Project FE Checkin

## 1. Run dev
```sh
yarn dev
```

## 2. Auto import (https://github.com/antfu/unplugin-auto-import)
Các hàm của vue, vue-router, pinia và các component của naiveui đều được auto import
```ts
// Good
const count = ref(0)

// Bad
import { ref } from 'vue'
const count = ref(0)
```

## 3. Chú ý các alias
```
@, @axios, @enums
```
