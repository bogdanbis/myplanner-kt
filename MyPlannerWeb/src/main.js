import '@/assets/main.scss'
import { autoAnimatePlugin } from '@formkit/auto-animate/vue';
import { createPinia } from 'pinia';
import { createApp } from 'vue'
import App from './App.vue'
import registerUiElements from './components/ui-elements/registerUiElements.js';
import router from './router.js';

const pinia = createPinia()
const app = createApp(App)

registerUiElements(app)

app
		.use(pinia)
		.use(router)
		.use(autoAnimatePlugin)

app.mount('#app')
