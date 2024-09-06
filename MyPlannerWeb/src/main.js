import '@/assets/main.scss'
import { createApp } from 'vue'
import App from './App.vue'
import registerUiElements from './components/ui-elements/registerUiElements.js';
import { createPinia } from 'pinia';

const pinia = createPinia()
const app = createApp(App)

registerUiElements(app)

app.use(pinia)
app.mount('#app')
