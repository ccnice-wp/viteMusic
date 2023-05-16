<script setup>
import { ref, onMounted } from 'vue'
import { SongDataService } from '@/api/api.js'
import { storeToRefs } from 'pinia'
import { userPlayInfo } from '@/store/userPlayInfo.ts' 

let playInfoStore = userPlayInfo()

let { playList, player, playIndex } = storeToRefs(playInfoStore)

const rankingList = ref([])
const playDuration = ref([])


onMounted(() => {
  SongDataService.getSongRankingList().then(res => {
    // console.log(res.data.data)
    rankingList.value = res.data.data
    // console.log(rankingList.value)
    for(let i = 0; i < rankingList.value.length; i++) {
    //获取音乐时长
    let duration
    let m
    let s
    //经测试，发现audio也可获取视频的时长
    //生成一个html标签
    let audioElement = new Audio(rankingList.value[i].url);
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
})

const playMusic = (value) => {
  playList.value = rankingList.value

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
<div>
    <div class="mt-8">
    <div class="title flex justify-between items-end">
      <div class="left pl-8 font-semibold text-2xl space-x-6">
       <span>排行榜前十</span>
      </div>
      <div class="right flex items-center mr-10">
        <div >
          <a @click="playMusic">
            <div class="flex w-14 h-10 rounded-lg hover:bg-gray-200 bg-gray-100 text-center">
              <span class="container m-auto">播放</span>
            </div>
          </a>
        </div>
        <div>
          <!-- <a @click="toMainView">
            <div class="flex w-14 h-10 rounded-r-lg bg-gray-100 hover:bg-gray-200 text-center">
              <span class="container m-auto">返回</span>  
            </div>
          </a> -->
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
        
        <div v-for="(item, key) in rankingList" :key="key">
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