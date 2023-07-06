FROM node:16 as builder
LABEL maintainer="Hoang Bui <mynamebvh@gmail.com>"

WORKDIR /app
COPY package.json .
RUN yarn install
COPY ./ .
RUN yarn build --mode production

FROM nginx:1.21.0-alpine as production
RUN touch /var/run/nginx.pid && \
  mkdir -p /var/cache/nginx && \
  chown -R nginx:nginx /var/cache/nginx /var/run/nginx.pid
USER nginx
COPY --chown=nginx:nginx --from=builder /app/dist /usr/share/nginx/html
COPY --chown=nginx:nginx nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]