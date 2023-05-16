<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-12 21:13:5
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-28 17:27:41
 * @FilePath: /musicClient/src/layout/mainView/mainCenter/recommendView/recommendView.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted, computed } from 'vue'
import { SongListDataService } from '@/api/api.js'
import { storeToRefs } from 'pinia'
import { userPlayInfo } from '@/store/userPlayInfo.ts' 
import { userDataInfo } from '@/store/userDataInfo.ts' 
import commentView from 'coms/core/comment/commentView.vue'
import moment from 'moment'

let playInfoStore = userPlayInfo()
let userDataStore = userDataInfo()
let { playList, player, playIndex } = storeToRefs(playInfoStore)
let { userData } = storeToRefs(userDataStore)

const songListData = ref([])
const musicList = ref([])
const songListDataView = ref(true)
const commentShow = ref(false)
const songList = ref({
  songListId: null,
  name: null,
  photo: null,
  introduction: null,
  createdTime: null
})

const playDuration = ref([]) 

onMounted(() => {
  SongListDataService.getSongList().then(res => {
    songListData.value = res.data.data
  })

  commentShow.value = false
})

const playMusic = (value) => {
  playList.value = musicList.value

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

const toSongListView = (value) => {
  
  songList.value = value

  SongListDataService.getSongListMusic(songList.value).then(res => {
    musicList.value = res.data.data

    for(let i = 0; i < musicList.value.length; i++) {
      //获取音乐时长
      let duration
      let m
      let s
      //经测试，发现audio也可获取视频的时长
      //生成一个html标签
      let audioElement = new Audio(musicList.value[i].url);
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

  songListDataView.value = !songListDataView.value
}

const showComment = () => {
  commentShow.value = !commentShow.value
}


const filterTableData = computed(() =>
  songListData.value.filter(
    (data) =>
      (data.createdTime = moment(new Date(data.createdTime)).format('YYYY-MM-DD'))
  )
)


</script>

<template>

  <div v-if="songListDataView" class="mt-8">
    <div class="title">
      <span class="text-gray-800 font-black text-4xl pl-6">推荐</span>
      <span class="block text-gray-800 font-semibold text-xl mt-2 pl-6">Hi {{ userData.name }} 今日为您推荐</span>
    </div>

    <div style="height: 525px;" class="overflow-y-auto flex-wrap items-center ml-6 mt-8 space-y-4 ">

      <div class="inline-block" v-for="(item,key) in filterTableData" :key="key">
        <a @click="toSongListView(item)" class="inline-block mr-4" >
          <div class="flex space-x-2">
            <div class="photo w-36 h-36 bg-red-300 rounded-lg">
              <img class="h-full w-full rounded-lg" :src="item.photo" alt="">
            </div>
            <div class="introduce space-y-2 w-52">
              <span class="font-semibold text-gray-600" >{{ item.introduction }}</span>
              <span class="block text-gray-300">{{ item.name }}</span>
              <span class="block text-gray-300">{{ item.createdTime }}</span>
             
            </div>
          </div>
        </a>
      </div>  
    </div>
  </div>

  <!-- 歌曲列表详情页 -->
  <div v-else class="mt-8">
    <div class="title flex justify-between items-end">
      <div class="left">
        <div class="flex space-x-2 ml-6">
            <div class="photo w-24 h-24 bg-red-300 rounded-lg">
              <img class="h-full w-full rounded-lg" :src="songList.photo" alt="">
            </div>
            <div class="introduce space-y-2 ">
              <span class="font-semibold text-gray-600" >{{ songList.introduction }}</span>
              <span class="block text-gray-300">{{ songList.name }}</span>
              <span class="block text-gray-300">{{ songList.createdTime }}</span>
            </div>
        </div>
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
          <a @click="showComment" >
            <div class="flex w-14 h-10 bg-gray-100 hover:bg-gray-200 text-center">
              <span v-show="!commentShow" class="container m-auto">评论</span>
              <span v-show="commentShow" class="container m-auto">歌曲</span>
            </div>
          </a>
        </div>
        <div>
          <a @click="toSongListView">
            <div class="flex w-14 h-10 rounded-r-lg bg-gray-100 hover:bg-gray-200 text-center">
              <span class="container m-auto">返回</span>  
            </div>
          </a>
        </div>
      </div>
    </div>

    <div style="height: 550px;" class=" ml-6 mr-6 mt-8 space-y-2 ">

      <div v-if="!commentShow">
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

        <div style="height: 468px;" class="flex-wrap overflow-x-hidden ov overflow-y-scroll">
          
          <div v-for="(item, key) in musicList" :key="key">
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

      <div v-else>
        <comment-view></comment-view>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>