export const useLocalStorage = (key) => ({
	key: key,
	get value() {
		return JSON.parse(localStorage.getItem(this.key));
	},
	set value(newVal) {
		localStorage.setItem(this.key, JSON.stringify(newVal));
	},
	remove() {
		localStorage.removeItem(this.key);
	},
})
