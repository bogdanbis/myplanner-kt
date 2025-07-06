import axios from 'axios';
import { useToast } from 'vue-toastification';
import AuthService from './AuthService.js';

export default class Api {
	static toast = useToast();
	params = null;
	authService = new AuthService(this);

	constructor(params) {
		const token = localStorage.getItem('token');
		if (token) {
			this.params = {
				...params,
				headers: { 'Authorization': 'Bearer ' + token },
			}
		} else {
			this.params = params;
		}
		this.caller = axios.create(this.params);
	}

	async logIn(email, password) {
		const response = await this.authService.logIn(email, password);
		const params = {
			...this.params,
			headers: { 'Authorization': 'Bearer ' + response.token },
		};
		this.caller = axios.create(params);
	}

	async signUp(user) {
		await this.authService.signUp(user);
		await this.logIn(user.email, user.password);
	}

	logOut() {
		this.authService.logOut();
		this.params.headers = null;
		this.caller = axios.create(this.params);
	}

	async get(resource, config = undefined, root = false, handle = true) {
		return await this.caller
				.get(resource, config)
				.then(res => root ? res : res.data)
				.catch(err => {
					if (handle)
						this.createErrorToast(err);
					throw err;
				});
	}

	async post(resource, body = undefined, config = undefined, handle = true) {
		return await this.caller
				.post(resource, body, config)
				.then(res => res.data)
				.catch(err => {
					if (handle)
						this.createErrorToast(err);
					throw err;
				});
	}

	async put(resource, body = undefined, config = undefined) {
		return await this.caller
				.put(resource, body, config)
				.then(res => res.data)
				.catch(err => {
					this.createErrorToast(err);
					throw err;
				});
	}

	async delete(resource, config = undefined) {
		return await this.caller
				.delete(resource, config)
				.then(res => res.data)
				.catch(err => {
					this.createErrorToast(err);
					throw err;
				});
	}

	async upload(resource, file, config = undefined) {
		const formData = new FormData();
		formData.append('file', file);
		let requestConfig = { headers: { 'Content-Type': 'multipart/form-data' }, ...config }
		return await this.caller
				.post(resource, formData, requestConfig)
				.then(res => res.data)
				.catch(err => {
					this.createErrorToast(err);
					throw err;
				});
	}

	async createErrorToast(error) {
		if (!error.response) {
			Api.toast.error('Could not establish connection to server.', { timeout: false });
			return;
		}
		let message;
		const code = error.response.status;
		if (code === 401 || code === 403) {
			this.logOut();
			localStorage.setItem('unauthorized', 'true');
			window.location = '/#/login';
			return;
		}
		if (code === 413) {
			message = 'File too large. Maximum allowed file size is 10MB.'
		} else if (code === 404) {
			message = 'Something was not found :('
		} else {
			let responseData = error.response.data;
			if (responseData.errorType) {
				message = Api.errorTypes[responseData.errorType];
			} else {
				let errorMessage = responseData?.errorMessage;
				if (responseData instanceof Blob)
					errorMessage = JSON.parse(await responseData.text()).errorMessage;
				if (errorMessage)
					message = 'An error occured: ' + errorMessage;
				else
					message = 'An error occured:';
			}
		}
		Api.toast.error(message);
	}

	static errorTypes = {
		entity_not_found: 'Something was not found :(',
		user_not_found: 'An account with this email does not exist.',
		email_used: 'An account with this email already exists.',
	}

}
