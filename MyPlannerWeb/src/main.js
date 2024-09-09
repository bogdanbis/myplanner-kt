import '@/assets/main.scss'
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

app.mount('#app')
