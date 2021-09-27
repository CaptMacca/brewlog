FROM node:12.16.1 as angular-built
WORKDIR ./src/app
COPY package.json package.json
RUN npm i -g @angular/cli@9.1.12
RUN npm ci
COPY . .
RUN ng build --prod --build-optimizer

FROM nginx:alpine
COPY --from=angular-built ./src/app/dist /usr/share/nginx/html
EXPOSE 80 443
CMD [ "nginx", "-g", "daemon off;" ]
