// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import my_login from './myLogin.vue'
import Join from "./components/JoinForm.vue"
import register from "./components/registerPage.vue"


const routes = [
  {
    path: '/',
    component: my_login   
  },
  {
    path: '/Join',
    component: Join
  },
  {
    path:'/jump-r',
    component:register
  }
]

const router = createRouter({
  history: createWebHistory(),  // 使用 HTML5 history 模式（漂亮 URL）
  routes
})

export default router
