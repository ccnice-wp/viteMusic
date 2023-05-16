/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 10:27:09
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-09 19:30:35
 * @FilePath: /musicManage/src/main.js
 * @Description: null
 */
import { createApp } from 'vue'
// import moment from 'moment'
import ElementPlus from 'element-plus'
import './style.css'
import 'animate.css'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'

//设置axios请求的地址默认是'/api',这样根据第一步中配置的会将/api替换为对应服务器地址
axios.defaults.baseURL = "/api";


const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}


app.use(router).use(ElementPlus).mount('#app')
