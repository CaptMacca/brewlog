// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  apiUrl: 'http://10.1.1.79:8080/',
  recipeApiUrl: 'http://10.1.1.79:8080/api/recipes',
  brewsApiUrl: 'http://10.1.1.79:8080/api/brews',
  measurementApiUrl: 'http://10.1.1.79:8080/api/measurement',
  recipeUploadApiUrl: 'http://10.1.1.79:8080/api/recipes/upload',
  loginUrl: 'http://10.1.1.79:8080/api/auth/signin',
  signupUrl: 'http://10.1.1.79::8080/api/auth/signup',
};
