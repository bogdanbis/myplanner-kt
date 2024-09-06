import ApplicationUser from '@/models/ApplicationUser';
import axios from 'axios';
import { useToast } from 'vue-toastification';

export default class Api {
	static toast = useToast();
	params = null;

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
		localStorage.removeItem('token');
		const logInResponse = await this.post('/login', { email, password }, undefined, false);
		const user = new ApplicationUser(logInResponse);
		if (logInResponse.token) {
			const params = {
				...this.params,
				headers: { 'Authorization': 'Bearer ' + logInResponse.token },
			};
			localStorage.setItem('token', logInResponse.token);
			this.caller = axios.create(params);
		}
		return user;
	}

	logOut() {
		localStorage.removeItem('token');
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

	async post(resource, body = null, config = undefined, handle = true) {
		return await this.caller
				.post(resource, body, config)
				.then(res => res.data)
				.catch(err => {
					if (handle)
						this.createErrorToast(err);
					throw err;
				});
	}

	async put(resource, body, config = undefined) {
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
			Api.toast.error('Nu s-a putut stabili conexiunea la server.', { timeout: false });
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
			message = 'Dimensiunea fișierului prea mare. Maximul admis este 10MB.'
		} else if (code === 404) {
			message = 'Ceea ce cauți pare că nu există.'
		} else {
			let responseData = error.response.data;
			if (responseData.errorType) {
				message = Api.errorTypes[responseData.errorType];
			} else {
				let errorMessage = responseData?.errorMessage;
				if (responseData instanceof Blob)
					errorMessage = JSON.parse(await responseData.text()).errorMessage;
				if (errorMessage)
					message = 'A apărut o eroare: ' + errorMessage;
				else
					message = 'A apărut o eroare';
			}
		}
		Api.toast.error(message);
	}

	static errorTypes = {
		entity_not_found: 'Ceea ce cauți pare că nu există.',
		user_not_found: 'Nu există niciun cont cu acest email.',
	}

}
