import Api from './Api.js';

const api = new Api({
	baseURL: import.meta.env.VITE_BASE_API_URL,
});

export default api;
