const dateFormatter = new Intl.DateTimeFormat('en-GB', { dateStyle: 'medium' });

const timeFormatter = new Intl.DateTimeFormat('en-GB', { timeStyle: 'short' });

const dateTimeFormatter = new Intl.DateTimeFormat('en-GB', {
	dateStyle: 'medium',
	timeStyle: 'short',
});

export function formatDate(value) {
	return value ? dateFormatter.format(new Date(value)) : undefined
}

export function formatDateTime(value) {
	return value ? dateTimeFormatter.format(new Date(value)) : undefined
}

export function relativeDate(value) {
	const date = new Date(value);
	const now = new Date();
	if (date.toDateString() === now.toDateString())
		return timeFormatter.format(date)
	else if (date.getUTCFullYear() === now.getUTCFullYear()
			&& date.getUTCMonth() === now.getUTCMonth()
			&& date.getUTCDate() === now.getUTCDate() - 1)
		return 'Yesterday';
	else
		return dateFormatter.format(date)
}
