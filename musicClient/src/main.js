/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-29 10:42:04
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-22 14:05:23
 * @FilePath: /musicClient/src/main.js
 * @Description: null
 */
import { createApp } from 'vue'
import './style.css'
import 'animate.css'
import ElementPlus from 'element-plus'
import App from './App.vue'
import axios from 'axios'
import router from './router'
import store from './store'


//设置axios请求地址默认是‘/api',这样根据第一步中配置会将/api替换为对应服务器地址
axios.defaults.baseURL = "/api";

createApp(App).use(router).use(ElementPlus).use(store).mount('#app')
