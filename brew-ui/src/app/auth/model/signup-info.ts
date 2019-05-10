export class SignUpInfo {
  firstName: string;
  surname: string;
  username: string;
  email: string;
  role: string[];
  password: string;

  constructor(firstName: string, surname: string, username: string, email: string, password: string) {
      this.firstName = firstName;
      this.surname = surname;
      this.username = username;
      this.email = email;
      this.password = password;
      this.role = ['user'];
  }
}
