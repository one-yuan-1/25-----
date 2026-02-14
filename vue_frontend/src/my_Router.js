// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import my_login from './components/myLogin.vue'
import Join from "./components/JoinForm.vue"


const routes = [
  {
    path: '/',
    component: my_login   
  },
  {
    path: '/Join',
    component: Join
  }
]

const router = createRouter({
  history: createWebHistory(),  // 使用 HTML5 history 模式（漂亮 URL）
  routes
})

export default router
