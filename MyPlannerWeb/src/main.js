import './assets/main.scss';
import 'js-circle-progress';
import { autoAnimatePlugin } from '@formkit/auto-animate/vue';
import { createPinia } from 'pinia';
import { createApp } from 'vue'
import App from './App.vue'
import registerUiElements from './components/ui-elements/registerUiElements.js';
import router from './router.js';
import { formatDate, formatDateTime, relativeDate } from './utils/dateFormatter.js';

const pinia = createPinia()
const app = createApp(App)

registerUiElements(app)

app
		.use(pinia)
		.use(router)
		.use(autoAnimatePlugin)

app.config.globalProperties.$date = formatDate;
app.config.globalProperties.$dateTime = formatDateTime;
app.config.globalProperties.$relativeDate = relativeDate;

app.mount('#app')
