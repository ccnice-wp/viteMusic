/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 11:07:14
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-03-31 11:46:49
 * @FilePath: /musicManage/src/router/index.js
 * @Description: null
 */
import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/index.vue'



const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      component: Layout,
      path: '/',
      children: [

      ]
    }
  ]
})

export default router



