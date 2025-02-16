import DOMPurify from 'dompurify';

export function sanitize(value) {
	if (typeof value === 'string') {
		return DOMPurify.sanitize(value, { ALLOWED_TAGS: [] })
				.replaceAll(/(?:https?|ftp):\/\/[\n\S]+/g, '');
	}
}
