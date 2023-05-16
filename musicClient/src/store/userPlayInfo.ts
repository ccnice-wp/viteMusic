/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-22 11:07:50
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-22 15:48:48
 * @FilePath: /musicClient/src/store/userPlayInfo.ts
 * @Description: null
 */
import { defineStore } from 'pinia'

export const userPlayInfo = defineStore('userPlayInfo', {
  state: () => {
    return {
      //当前播放歌曲
      player: {
        songId: null,
        name: "",
        album: "",
        url: "",
        lyric: [] as any[]
      },
      //歌单
      playList: [],
      //播放音量
      playVolume: 0,
      //切换模式
      playType: 1,
      //侧边滑块
      playSlider: false,
      //在列表中的位置
      playIndex: 0,
      //是否自动播放
      playAuto: false,
      addLoveView: false,
      //最近播放歌单
      recentlyList: []
    }
  },
  getters: {

  },
  actions: {
    updatePlayList( val: [] ) {
      this.playList = val
    },
    updatePlayVolume( val: number ) {
      this.playVolume = val
    }
  },
  //持久化存储
  persist: {
    //开启持久化存储默认是绘画存储,页面关闭后消失
    enabled: true,
    strategies: [{
      key: 'userPlayInfo',
      storage: localStorage
    }]
  }
})