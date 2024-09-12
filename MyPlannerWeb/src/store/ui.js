import { defineStore } from 'pinia';

export const useUiStore = defineStore('$ui', {
	state: () => ({
		scheme: 'auto',
		preferedScheme: 'auto',
		invoiceTemplate: {
			showInvoice: false,
			showProduct: false,
		},
	}),

	actions: {
		setColorScheme(scheme) {
			if (scheme === 'auto') {
				localStorage.setItem('prefered-color-scheme', 'auto');
				const preferesDarkColorScheme = window.matchMedia('(prefers-color-scheme: dark)');
				if (preferesDarkColorScheme.matches)
					document.documentElement.classList.add('dark');
				else
					document.documentElement.classList.remove('dark');
			} else if (scheme === 'light') {
				document.documentElement.classList.remove('dark');
			} else if (scheme === 'dark') {
				document.documentElement.classList.add('dark');
			}
			this.scheme = scheme;
		},
		setPreferedScheme(scheme) {
			this.preferedScheme = scheme;
			localStorage.setItem('prefered-color-scheme', scheme);
		},
		initPreferedColorScheme() {
			let preferedColorScheme = localStorage.getItem('prefered-color-scheme') || 'auto';
			this.setColorScheme(preferedColorScheme);
			this.setPreferedScheme(preferedColorScheme);

			const preferesDarkColorScheme = window.matchMedia('(prefers-color-scheme: dark)');
			preferesDarkColorScheme.addEventListener('change', e => {
				let preferedColorScheme = localStorage.getItem('prefered-color-scheme') || 'auto';
				if (preferedColorScheme === 'auto')
					this.setColorScheme(e.matches && 'dark' || 'light');
			});
		},
		changeColorScheme() {
			if (this.scheme === 'light')
				this.setColorScheme('dark');
			else if (this.scheme === 'dark')
				this.setColorScheme('auto');
			else if (this.scheme === 'auto')
				this.setColorScheme('light');
			this.setPreferedScheme(this.scheme);
		},
		setInvoiceTemplate(showInvoice, showProduct) {
			this.invoiceTemplate.showInvoice = showInvoice;
			this.invoiceTemplate.showProduct = showProduct;
		},
	},
})
