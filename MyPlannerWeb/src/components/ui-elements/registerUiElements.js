import Toast from 'vue-toastification';
import MpButton from './buttons/MpButton.vue';
import MpLinkButtonWithConfirm from './buttons/MpLinkButtonWithConfirm.vue';
import MpForm from './form/MpForm.vue';
import MpFormCheckbox from './form/MpFormCheckbox.vue';
import MpFormInput from './form/MpFormInput.vue';
import MpFormNumberInput from './form/MpFormNumberInput.vue';
import MpFormSection from './form/MpFormSection.vue';
import MpFormSectionTitle from './form/MpFormSectionTitle.vue';
import MpFormSelect from './form/MpFormSelect.vue';
import MpFormTextarea from './form/MpFormTextarea.vue';
import MpSearch from './form/MpSearch.vue';
import MpCol from './grid/MpCol.vue';
import MpRow from './grid/MpRow.vue';
import LoadingIcon from './icons/LoadingIcon.vue';
import MpIcon from './icons/MpIcon.vue';
import MpBackLink from './MpBackLink.vue';
import MpCard from './MpCard.vue';
import MpDialog from './MpDialog.vue';
import MpFileUpload from './MpFileUpload.vue';
import MpInlineValue from './MpInlineValue.vue';
import MpLink from './MpLink.vue';
import MpMultilineText from './MpMultilineText.vue';
import MpTable from './MpTable.vue';
import ProgressBar from './progress/ProgressBar.vue';
import ProgressCircle from './progress/ProgressCircle.vue';

export default function registerUiElements(Vue) {
	Vue.component('MpCard', MpCard);
	Vue.component('MpButton', MpButton);
	Vue.component('MpLinkButtonWithConfirm', MpLinkButtonWithConfirm);
	Vue.component('MpIcon', MpIcon);
	Vue.component('LoadingIcon', LoadingIcon);
	Vue.component('MpDialog', MpDialog);
	Vue.component('MpFileUpload', MpFileUpload);
	Vue.component('MpLink', MpLink);
	Vue.component('MpBackLink', MpBackLink);
	Vue.component('MpInlineValue', MpInlineValue);
	Vue.component('MpMultilineText', MpMultilineText);
	Vue.component('MpTable', MpTable);

	// forms
	Vue.component('MpForm', MpForm);
	Vue.component('MpFormSection', MpFormSection);
	Vue.component('MpFormSectionTitle', MpFormSectionTitle)
	Vue.component('MpFormCheckbox', MpFormCheckbox);
	Vue.component('MpFormInput', MpFormInput);
	Vue.component('MpFormNumberInput', MpFormNumberInput);
	Vue.component('MpFormSelect', MpFormSelect);
	Vue.component('MpFormTextarea', MpFormTextarea);
	Vue.component('MpSearch', MpSearch);

	// grid
	Vue.component('MpCol', MpCol);
	Vue.component('MpRow', MpRow);

	// progress
	Vue.component('ProgressBar', ProgressBar);
	Vue.component('ProgressCircle', ProgressCircle);

	// plug-ins
	Vue.use(Toast, {
		draggable: false,
		hideProgressBar: true,
		icon: false,
		transition: 'fade',
	});
}
