/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-26 21:25:27
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-26 22:27:30
 * @FilePath: /musicClient/src/store/searchDataInfo.ts
 * @Description: null
 */
import { defineStore } from 'pinia'

export const searchDataInfo = defineStore('searchDataInfo', {
  state: () => {
    return {
      searchList: [],
      showSearchList: false,
      searchValue: null
    }
  },
  getters: {

  },
  actions: {

  },
  //持久化存储
  persist: {
    //开启持久化存储默认是绘画存储,页面关闭后消失
    enabled: true,
    strategies: [{
      key: 'searchDataInfo',
      storage: localStorage
    }]
  }
})