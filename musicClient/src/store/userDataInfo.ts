/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-25 18:47:30
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-13 13:46:16
 * @FilePath: /musicClient/src/store/userDataInfo.ts
 * @Description: null
 */
import { defineStore } from 'pinia'

export const userDataInfo = defineStore('userDataInfo', {
  state: () => {
    return {
      userData: {
        userId: null,
        name: null,
        gender: null,
        birth: null,
        location: null,
        photo: null,
        introduction: null
      },
      userDataEdit: false,
      userViewIndex: 1,
      //用户自建某单个歌单信息
      userSongListOne: null,
      //用户自建歌单列表
      userSongListData: []
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
      key: 'userDataInfo',
      storage: localStorage
    }]
  }
})