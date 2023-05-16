/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-22 10:18:46
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-22 14:33:26
 * @FilePath: /musicClient/src/store/index.ts
 * @Description: null
 */
import { createPinia } from 'pinia'
//引入持久化插件
import  piniaPluginPersist  from 'pinia-plugin-persist'

const store = createPinia()

store.use(piniaPluginPersist)

export default store
