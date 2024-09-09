import Toast from 'vue-toastification';
import MpButton from './buttons/MpButton.vue';
import MpForm from './form/MpForm.vue';
import MpFormCheckbox from './form/MpFormCheckbox.vue';
import MpFormInput from './form/MpFormInput.vue';
import MpFormNumberInput from './form/MpFormNumberInput.vue';
import MpFormSelect from './form/MpFormSelect.vue';
import MpFormTextarea from './form/MpFormTextarea.vue';
import MpCol from './grid/MpCol.vue';
import MpRow from './grid/MpRow.vue';
import LoadingIcon from './icons/LoadingIcon.vue';
import MpIcon from './icons/MpIcon.vue';
import MpCard from './MpCard.vue';
import MpDialog from './MpDialog.vue';

export default function registerUiElements(Vue) {
	Vue.component('MpCard', MpCard);
	Vue.component('MpButton', MpButton);
	Vue.component('MpIcon', MpIcon);
	Vue.component('LoadingIcon', LoadingIcon);
	Vue.component('MpDialog', MpDialog);

	// forms
	Vue.component('MpForm', MpForm);
	Vue.component('MpFormCheckbox', MpFormCheckbox);
	Vue.component('MpFormInput', MpFormInput);
	Vue.component('MpFormNumberInput', MpFormNumberInput);
	Vue.component('MpFormSelect', MpFormSelect);
	Vue.component('MpFormTextarea', MpFormTextarea);

	// grid
	Vue.component('MpCol', MpCol);
	Vue.component('MpRow', MpRow);

	// plug-ins
	Vue.use(Toast, {
		draggable: false,
		hideProgressBar: true,
		icon: false,
		transition: 'fade',
	});
}
