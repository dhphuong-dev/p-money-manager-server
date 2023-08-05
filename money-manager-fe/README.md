# FE pMoney Manager

Project frontend pMoney Manager

## Cài đặt

1. Cài đặt NodeJS (nên sử dụng v18)

## Cấu hình

1. Tạo file .env dựa vào file mẫu (.env.example) bằng câu lệnh dưới (ubuntu)

```bash
cp .env.example .env
```

- Chú thích env

```
VITE_API_URL=http://localhost:3000 //baseURL API
VITE_RECAPTCHA_SITE_KEY=6LdXOKImAAAAABtefF-VkpXTH7YNl1wnrYZ2RHO9 // public key recaptcha

```

2. Tải các dependencies.

```bash
yarn
```

## Mục lục

- [FE pMoney Manager](#fe-pmoney-manager)
  - [Cài đặt](#cài-đặt)
  - [Cấu hình](#cấu-hình)
  - [Mục lục](#mục-lục)
  - [Đặc trưng](#đặc-trưng)
  - [Dòng lệnh](#dòng-lệnh)

## Đặc trưng

- **Framework**: [Vue](https://vuejs.org/)
- **Router**: [Vue Router](https://router.vuejs.org/)
- **State Management**: [Pinia](https://pinia.vuejs.org/)
- **Style**: [Scss](https://sass-lang.com/)
- **Icon**: [Tabler](https://tabler-icons.io/)
- **Vite plugin**: [unplugin-auto-import](https://github.com/antfu/unplugin-auto-import), [unplugin-vue-components](https://github.com/antfu/unplugin-vue-components), [vite-plugin-pages](https://github.com/hannoeru/vite-plugin-pages), [vite-plugin-vue-layouts](https://github.com/JohnCampionJr/vite-plugin-vue-layouts)

## Dòng lệnh

1. Chạy môi trường dev

```bash
yarn dev
```

2. Build

```bash
yarn build
```

3. Docker

```bash
sudo docker compose up -d
```
