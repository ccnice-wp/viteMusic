/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-09 19:26:21
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-26 13:57:21
 * @FilePath: /musicClient/src/router/index.js
 * @Description: null
 */
import { createRouter, createWebHistory } from "vue-router";
import Index from '@/layout/index.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      component: Index,
      path: "/",
      meta: {
        title: 'Vite 音乐'
      }
    },
    {
      path: "/test",
      name: "test",
      component: () => import('@/layout/sideBar/test.vue')
    },
    {
      path: "/login",
      name: "login",
      component: () => import('coms/core/login/index.vue'),
      meta: {
        title: '登录'
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  if(to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

export default router