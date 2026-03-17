// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import my_login from './components/myLogin.vue'
import Join from "./components/JoinForm.vue"
import register from "./components/registerPage.vue"
import view_others from './components/view_select.vue'
import view_random from './components/view_random.vue'

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
  },
  {
    path: '/edit',
    name: 'EditText',
    component: () => import('@/components/EditTest.vue')
  },
  {
    path: '/view_others',
    component: view_others,
  },
  {
    path:'/view_random',
    component:view_random
  }
]

const router = createRouter({
  history: createWebHistory(),  // 使用 HTML5 history 模式（漂亮 URL）
  routes
})

export default router
