<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-26 21:21:30
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-28 17:28:38
 * @FilePath: /musicClient/src/layout/mainView/navBar/searchList.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { searchDataInfo } from '@/store/searchDataInfo.ts'
import { userPlayInfo } from '@/store/userPlayInfo.ts' 

let playInfoStore = userPlayInfo()
let searchDataStore = searchDataInfo()

let { playList, player, playIndex } = storeToRefs(playInfoStore)
let { searchList, showSearchList, searchValue } = storeToRefs(searchDataStore)

const playDuration = ref([])

const toMainView = () => {
  showSearchList.value = false
}

onMounted(() => {
  for(let i = 0; i < searchList.value.length; i++) {
    //获取音乐时长
    let duration
    let m
    let s
    //经测试，发现audio也可获取视频的时长
    //生成一个html标签
    let audioElement = new Audio(searchList.value[i].url);
    audioElement.addEventListener('loadeddata', () => {
      duration = audioElement.duration
      m = '0' + Math.floor(duration/60)
      if( duration % 60 >= 10 ) {
        s = Math.ceil(duration % 60)
      } else {
        s = '0' + Math.ceil(duration % 60)
      }

      playDuration.value.push(m+':'+s)

    })
  }
})

const playMusic = (value) => {
  playList.value = searchList.value

  if(typeof value.songId === 'undefined') {
    value.songId = playList.value[0].songId
  }

  // console.log(value.songId)

  for(let i = 0; i < playList.value.length; i++) {
    if(playList.value[i].songId === value.songId) {
      if(playIndex.value === i) {
        playIndex.value = -1
      } else {
        playIndex.value = i
      }
      break
    }
  }
}

</script>

<template>
  <!-- 搜索列表详情页 -->
  <div>
    <div class="mt-8">
    <div class="title flex justify-between items-end">
      <div class="left pl-8 font-semibold text-2xl space-x-6">
       <span>搜索</span>
       <span class="text-green-300">{{ searchValue }}</span>
      </div>
      <div class="right flex items-center mr-10">
        <div >
          <a @click="playMusic">
            <div class="flex w-14 h-10 rounded-l-lg hover:bg-gray-200 bg-gray-100 text-center">
              <span class="container m-auto">播放</span>
            </div>
          </a>
        </div>
        <div>
          <a @click="toMainView">
            <div class="flex w-14 h-10 rounded-r-lg bg-gray-100 hover:bg-gray-200 text-center">
              <span class="container m-auto">返回</span>  
            </div>
          </a>
        </div>
      </div>
    </div>

    <div style="height: 550px;" class=" ml-6 mr-6 mt-8 space-y-2 ">

      <div class="text-lg border-b-2 border-gray-200">
        <div class="h-10 w-full flex items-center  justify-between rounded-md">
            <div class="w-4/12 pl-2">
              歌曲
            </div>
            <div class="w-3/12">
              歌手
            </div>
            <div class="w-3/12">
              专辑
            </div>
            <div class="w-2/12">
              时长
            </div>
        </div>
      </div> 

      <div style="height: 480px;" class="flex-wrap overflow-x-hidden overflow-y-scroll">
        
        <div v-for="(item, key) in searchList" :key="key">
          <a @click="playMusic(item)" class="">
            <div class="h-10 flex hover:bg-gray-200 items-center justify-between rounded-md">
              <div class="w-4/12 pl-2">
                {{ item.name }}
              </div>
              <div class="w-3/12 pl-1">
                {{ item.singer }}
              </div>
              <div class="w-3/12 pl-2">
                {{ item.album }}
              </div>
                <div class="w-2/12 pl-3">
                {{ playDuration[key] }}
                </div>
              </div>
            </a> 
          </div>

      </div>

    </div>
    </div>
  </div>
</template>

<style scoped>

</style>