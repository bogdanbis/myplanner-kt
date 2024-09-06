export default class ApplicationUser {
	firstName;
	lastName;
	email;

	constructor(logInResponse) {
		if (!logInResponse) return;
		this.firstName = logInResponse.firstName;
		this.lastName = logInResponse.lastName;
		this.email = logInResponse.email;
	}

	get fullName() {
		return this.firstName + ' ' + this.lastName;
	}
}
