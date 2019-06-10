# FROM johnpapa/angular-cli as angular-built
# Using the above image allows us toskip the angular-cli install
FROM node:10.16 as angular-built
WORKDIR /usr/src/app
COPY package.json package.json
COPY package-lock.json package-lock.json
RUN npm i -g @angular/cli@7.3.6
RUN npm ci --production
COPY . .
RUN ng build --prod --build-optimizer

FROM nginx:alpine
COPY --from=angular-built /usr/src/app/dist /usr/share/nginx/html
EXPOSE 80 443
CMD [ "nginx", "-g", "daemon off;" ]
