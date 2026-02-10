import { createApp } from 'vue'
import App from './App.vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/no_border.css'

const app = createApp(App)
// 2. 全局注册 Element-Plus
app.use(ElementPlus)
app.mount('#app')
