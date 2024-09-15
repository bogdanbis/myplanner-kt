const dateFormatter = new Intl.DateTimeFormat('en-GB', {
	dateStyle: 'medium',
	timeStyle: 'short',
});

export function formatDate(date) {
	return date ? dateFormatter.format(new Date(date)) : undefined
}
