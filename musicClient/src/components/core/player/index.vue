<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-13 10:15:44
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-08 14:04:10
 * @FilePath: /musicClient/src/components/core/player/index.vue
 * @Description: null
-->
<script lang="ts" setup>
import { ref, onMounted, watch, computed, reactive, mergeProps, h } from 'vue'
import { SongDataService } from '@/api/api.js'
import * as musicMetadata from 'music-metadata-browser'
import { storeToRefs } from 'pinia'
import { ElMessage } from 'element-plus'
import { userPlayInfo } from '@/store/userPlayInfo.ts'
import { userDataInfo } from '../../../store/userDataInfo'

let playInfoStore = userPlayInfo()
let userDataStore = userDataInfo()

let { userData } = storeToRefs(userDataStore)
let { playList,recentlyList, playVolume, playType, player, playSlider, playIndex, addLoveView } = storeToRefs(playInfoStore)

const play = ref(false)
const open = ref(false)
const addPlayCounter = ref(0)
const addRecentlyCounter = ref(0)
const oldPlayIndex = ref(0)
// const musicList = ref([] as any[])
const musicListIndex = ref(0)
const progress = ref(0)
const plan = ref('0%')
const image = ref('')
const lyricOne = ref('')
const currentTime = reactive({
  m: 0,
  s: '00'
})
const totalTime = reactive({
  m: 0,
  s: 0
})
const volumeCopy = ref(0)
const audioRef = ref<HTMLAudioElement>()

onMounted(async () => {
  SongDataService.getSongDataList().then(async res => {
    //获取到播放音乐列表
    // playInfoStore.updatePlayList(res.data.data)

    // player.value.lyric =  parseLrc(playList.value[0].lyric)
    // player.value.name = playList.value[0].name
    // player.value.album = playList.value[0].album
    // player.value.songId = playList.value[0].songId
    // player.value.url = playList.value[0].url

    //初始化播放歌曲
    audioRef.value!.src =  player.value.url
    audioRef.value!.volume = playVolume.value * 0.01

    

    lyricOne.value = player.value.lyric[0]?.words

    audioToImage(audioRef.value!.src).then( res => {
      image.value = res
    })
  })

  //找到歌曲在列表中的index
  if(player.value.songId != null) {
    for(let i = 0; i < playList.value.length; i++) {
      if(playList.value[i] === player.value.songId) {
        playIndex.value = i
      }
    }
  }
})

//改变播放模式,顺序,随机,单曲循环
const changePlayType = () => {
  if(playType.value === 3) {
    playType.value = 1
  } else {
    playType.value = playType.value + 1
  }
}


//切换歌曲时更新播放中的歌曲信息
watch(playIndex, (newValue,oldValue) => {

  addPlayCounter.value = 0
  addRecentlyCounter.value = 0
  oldPlayIndex.value = Number(oldValue)
  //解决点击的歌曲的index与原来的index相同时音乐不切换的问题
  if(newValue === -1) {
    //更改歌曲的url
    audioRef.value!.src = playList.value[oldValue].url
    player.value.url = playList.value[oldValue].url
    //更改歌词
    player.value.lyric = parseLrc(playList.value[oldValue].lyric)
    lyricOne.value = player.value.lyric[0].words
    //更改歌曲名字和专辑名,和歌曲ID
    player.value.songId = playList.value[oldValue].songId
    player.value.name = playList.value[oldValue].name
    player.value.album = playList.value[oldValue].album

    playIndex.value = oldValue
  } else {
    //更改歌曲的url
    audioRef.value!.src = playList.value[newValue].url
    player.value.url = playList.value[newValue].url
    //更改歌词
    player.value.lyric = parseLrc(playList.value[newValue].lyric)
    lyricOne.value = player.value.lyric[0].words
    //更改歌曲名字和专辑名,和歌曲ID
    player.value.songId = playList.value[newValue].songId
    player.value.name = playList.value[newValue].name
    player.value.album = playList.value[newValue].album
  }
  
  //更新专辑封面
  audioToImage(audioRef.value!.src).then( res => {
    image.value = res
  })

  // audioRef.value!.autoplay = play.value
  if(!play.value) {
    play.value = !play.value
    audioRef.value!.autoplay = play.value
  } else {
    audioRef.value!.autoplay = play.value
  }
  
})


//音乐切换
const songSwitch = (action: boolean) => {
  if(playType.value === 1){
    //顺序播放
    if(action) {
      //顺序播放下一曲
      if(playIndex.value === playList.value.length - 1) {
        playIndex.value = 0
      } else {
        playIndex.value = playIndex.value + 1
      }
      
    } else {
      //顺序播放上一曲
      if(playIndex.value === 0) {
        playIndex.value = playList.value.length - 1
      } else {
        playIndex.value = playIndex.value - 1
      }
    }
  } else if(playType.value === 2) {
    //单曲循环
    audioRef.value!.currentTime = 0
    if(play.value) {
      audioRef.value?.play()
    } else {
      audioRef.value?.pause()
    }
  } else {
    //随机播放
    let index = getRandomInt(0, playList.value.length - 1)
    while(index === playIndex.value) {
      console.log("1")
      index = getRandomInt(0, playList.value.length - 1)
    }
    playIndex.value = index
  }
  
}
//调出侧边滑块歌单
const toggle = ()=> {
  playSlider.value = !playSlider.value
}

//生成随机数的下一次播放音乐的Index
const getRandomInt = (min: number, max: number) =>  {
  return Math.floor(Math.random() * (max - min + 1)) + min
}


//获取歌曲的专辑封面
const audioToImage = async (url: string) => {
  const metadata = await musicMetadata.fetchFromUrl(audioRef.value!.src);
  const pictures = metadata.common.picture
  return URL.createObjectURL(new Blob([pictures![0].data], { type: pictures![0].format }))
  
}

/**
 * 解析歌词字符串
 * 得到一个歌词字符串数组
 * 每个歌词字符串数组
 * {time:开始时间， words: 歌词内容}
 */

const parseLrc = (strLyric) => {
  let result = [] as any[]
  let lines = strLyric.split('\n')
  for(let i = 0; i < lines.length; i++) {
    let str = lines[i].split(']')
    let obj = {
      time: parseTime(str[0].substring(1)),
      words: str[1],
    }
    result.push(obj)
  }
  return result
}

/**
 * 将时间字符串解析为数字（秒）
 * 字符串变数字前面加个+号
 */

const parseTime = (strTime: string) => {
  let str = strTime.split(':')
  return +str[0] * 60 + +str[1]
}


const getDuration = () => {
  //获取歌曲的总播放时长m:s
  totalTime.m = Math.floor(audioRef.value!.duration / 60)
  totalTime.s = Math.ceil(audioRef.value!.duration) % 60
  lyricOne.value = player.value.lyric[0]?.words

}



const setCurrentTime = () => {
  //监控更新播放时间
  progress.value = Math.floor((audioRef.value!.currentTime / audioRef.value!.duration) * 100)
  currentTime.m = Math.floor(audioRef.value!.currentTime / 60)
  if((Math.floor(audioRef.value!.currentTime) % 60) < 10) {
    currentTime.s = "0" + (Math.floor(audioRef.value!.currentTime) % 60)
  } else {
    currentTime.s = (Math.floor(audioRef.value!.currentTime) % 60) + ""
  }

  //更新进度条
  plan.value =  (audioRef.value!.currentTime / audioRef.value!.duration) * 100 + "%"

  //更新最近播放列表
  if (audioRef.value!.currentTime > 0 && addRecentlyCounter.value === 0) {

    // console.log(playList.value[playIndex.value])
    // console.log(typeof recentlyList.value[0].songId)

    for(let i = 0; i < recentlyList.value.length; i++) {
      if (typeof recentlyList.value[0].songId === 'undefined') {
        break
      }
      if (recentlyList.value[i].songId === playList.value[playIndex.value].songId) {
        recentlyList.value.splice(i, 1)
      }
    }

    if(recentlyList.value.length >= 100) {
      recentlyList.value.splice(0, 1)
    }

    recentlyList.value.push(playList.value[playIndex.value])

    // recentlyList.value.length = 0

    addRecentlyCounter.value = addRecentlyCounter.value + 1
  }

  //更新音乐的播放次数
  if ((audioRef.value!.currentTime / audioRef.value!.duration) > 0.5 && addPlayCounter.value === 0) {
    if (typeof playList.value[playIndex.value] === 'undefined') {
      SongDataService.updateSongRankingList(playList.value[oldPlayIndex.value]).then(res => {
      })
    } else {
      SongDataService.updateSongRankingList(playList.value[playIndex.value]).then(res => {
      })
    }
    addPlayCounter.value = addPlayCounter.value + 1
  }

  //更新显示的歌词
  for(let i = 0; i < player.value.lyric.length; i++) {
    if(player.value.lyric[i].time - 0.2 > audioRef.value!.currentTime) {
      lyricOne.value = player.value.lyric[i-1]?.words
      break
    }
  }

}

//监控用户键盘输入控制音乐暂停与播放，音量控制,切换歌曲等
window.addEventListener('keyup', (event) => {
  if(event.key === ' ' && document.activeElement!.tagName === 'BODY') {
    playOrPause()
  }

  if(event.key === 'ArrowUp') {
      if(Math.ceil(+playVolume.value / 10) < 10 ) {
        playVolume.value = Math.floor(+playVolume.value + 10)
      } else {
        playVolume.value =  100
      }
  }
  if(event.key === 'ArrowDown') {
    if(Math.floor(+playVolume.value / 10) > 0 ) {
      playVolume.value = +playVolume.value - 10
    } else {
      playVolume.value = 0
    }
  }
  if(event.key == 'ArrowRight') {
    songSwitch(true)
  }
  if(event.key == 'ArrowLeft') {
    songSwitch(false)
  }

})

//更新音乐播放进度
const updateProgress = () => {
  audioRef.value!.currentTime = (progress.value / 100.0) * audioRef.value!.duration
}


watch(playVolume, (newVolume) => {
  //更新音量
  audioRef.value!.volume = +newVolume * 0.01
})

const silence = () => {

  //点击音标按钮切换静音和外放
  if(playVolume.value > 0) {
    volumeCopy.value = playVolume.value
    playVolume.value = 0
  } else {
    playVolume.value = volumeCopy.value
  }
}

const playOrPause = () => {

  //播放与暂停图标切换
  play.value = !play.value

  //控制音乐的播放与暂停
  if(play.value) {
    audioRef.value?.play()
  } else {
    audioRef.value?.pause()
  }
}


//把音乐添加进自己喜欢的歌单
const addLove = () => {

  if(userData.value.name === null || userData.value.name === ' ') {
    ElMessage({
          message: '请先登录',
          type: 'error',
        })
  } else {
    addLoveView.value = !addLoveView.value
  }
}


</script>

<template>
  <footer style="height: 78px;" class="border-t-2 border-gray-300">
    <div class="flex items-center justify-between">
      <div class="space-x-2 ml-8 mt-3 flex items-start">
        <a href="">
          <div class="rounded-sm bg-purple-600 h-14 w-14">
            <img v-if="image != ''" class="rounded-sm" :src="image" alt="">
          </div>
        </a>
        <div>
          <span class="text-gray-400 text-sm">{{ player.name }} - {{ player.album }}</span>
          <span class=" block text-sm">{{ lyricOne }}</span>
        </div>
      </div><!--end left-->
      <div class="flex items-center space-x-6">
        <div class="group relative player-time flex-wrap items-center text-center">
          <span class="text-gray-500 text-sm">{{ "0" + currentTime.m }}:{{ currentTime.s }} / {{"0" + totalTime.m }}:{{ totalTime.s }}</span>
          <div class="h-1 w-28 bg-gray-200 rounded-md">
            <div :style="{width: plan}" class="h-1 bg-gray-400 rounded-md"></div>
          </div>
          <div class="flex items-center absolute z-50 bg-white text-gray-700 text-sm -top-8 -left-10 h-8  w-52 shadow-lg ring-1 ring-black ring-opacity-5 py-2 rounded-md scale-0 group-hover:scale-100 duration-300 origin-bottom">
            <input @click="updateProgress" class="absolute text-gray-300 w-48 fill-current left-2 top-18 transform" type="range" v-model="progress" min="0" max="100">
          </div>
        </div><!--end play time-->
        <div class="tools flex items-center space-x-2">
          <a @click="addLove">
            <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12z" />
            </svg>
          </a>
          <a class="group relative">
            <svg v-if="playVolume > 66" class="w-5 h-5 animate__animated animate__bounceIn"  viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg"><title/><path d="M126,192H56a8,8,0,0,0-8,8V312a8,8,0,0,0,8,8h69.65a15.93,15.93,0,0,1,10.14,3.54l91.47,74.89A8,8,0,0,0,240,392V120a8,8,0,0,0-12.74-6.43l-91.47,74.89A15,15,0,0,1,126,192Z" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M320,320c9.74-19.38,16-40.84,16-64,0-23.48-6-44.42-16-64" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/>
              <path d="M368,368c19.48-33.92,32-64.06,32-112s-12-77.74-32-112" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M416,416c30-46,48-91.43,48-160S446,143,416,96" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/>
            </svg>
            <svg v-else-if="playVolume > 33" class="w-5 h-5 animate__animated animate__bounceIn" viewBox="0 0 512 512"  xmlns="http://www.w3.org/2000/svg"><title/>
              <path d="M157.65,192H88a8,8,0,0,0-8,8V312a8,8,0,0,0,8,8h69.65a16,16,0,0,1,10.14,3.63l91.47,75A8,8,0,0,0,272,392.17V119.83a8,8,0,0,0-12.74-6.44l-91.47,75A16,16,0,0,1,157.65,192Z" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M352,320c9.74-19.41,16-40.81,16-64,0-23.51-6-44.4-16-64" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M400,368c19.48-34,32-64,32-112s-12-77.7-32-112" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/>
            </svg>
            <svg v-else-if="playVolume > 0" class="w-5 h-5 animate__animated animate__bounceIn" viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg"><title/>
              <path d="M189.65,192H120a8,8,0,0,0-8,8V312a8,8,0,0,0,8,8h69.65a16,16,0,0,1,10.14,3.63l91.47,75A8,8,0,0,0,304,392.17V119.83a8,8,0,0,0-12.74-6.44l-91.47,75A16,16,0,0,1,189.65,192Z" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M384,320c9.74-19.41,16-40.81,16-64,0-23.51-6-44.4-16-64" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/>
            </svg>
            <svg v-else  class=" w-5 h-5 animate__animated animate__bounceIn"  viewBox="0 0 512 512"  xmlns="http://www.w3.org/2000/svg"><title/><line style="fill:none;stroke:#000;stroke-linecap:round;stroke-miterlimit:10;stroke-width:32px" x1="416" x2="64" y1="432" y2="80"/>
              <path d="M224,136.92v33.8a4,4,0,0,0,1.17,2.82l24,24a4,4,0,0,0,6.83-2.82V120.57a24.53,24.53,0,0,0-12.67-21.72,23.91,23.91,0,0,0-25.55,1.83,8.27,8.27,0,0,0-.66.51l-31.94,26.15a4,4,0,0,0-.29,5.92l17.05,17.06a4,4,0,0,0,5.37.26Z"/><path d="M224,375.08l-78.07-63.92A32,32,0,0,0,125.65,304H64V208h50.72a4,4,0,0,0,2.82-6.83l-24-24A4,4,0,0,0,90.72,176H56a24,24,0,0,0-24,24V312a24,24,0,0,0,24,24h69.76l91.36,74.8a8.27,8.27,0,0,0,.66.51A23.93,23.93,0,0,0,243.63,413,24.49,24.49,0,0,0,256,391.45V341.28a4,4,0,0,0-1.17-2.82l-24-24a4,4,0,0,0-6.83,2.82ZM125.82,336Z"/><path d="M352,256c0-24.56-5.81-47.88-17.75-71.27a16,16,0,0,0-28.5,14.54C315.34,218.06,320,236.62,320,256q0,4-.31,8.13a8,8,0,0,0,2.32,6.25l19.66,19.67a4,4,0,0,0,6.75-2A146.89,146.89,0,0,0,352,256Z"/><path d="M416,256c0-51.19-13.08-83.89-34.18-120.06a16,16,0,0,0-27.64,16.12C373.07,184.44,384,211.83,384,256c0,23.83-3.29,42.88-9.37,60.65a8,8,0,0,0,1.9,8.26l16.77,16.76a4,4,0,0,0,6.52-1.27C410.09,315.88,416,289.91,416,256Z"/><path d="M480,256c0-74.26-20.19-121.11-50.51-168.61a16,16,0,1,0-27,17.22C429.82,147.38,448,189.5,448,256c0,47.45-8.9,82.12-23.59,113a4,4,0,0,0,.77,4.55L443,391.39a4,4,0,0,0,6.4-1C470.88,348.22,480,307,480,256Z"/>
            </svg>
            <div class="flex items-center absolute z-50 bg-white text-gray-700 text-sm -top-52 -left-2 h-52  w-8 shadow-lg ring-1 ring-black ring-opacity-5 py-2 rounded-md scale-0 group-hover:scale-100 duration-300 origin-bottom">
              <input class="absolute text-gray-300 fill-current -left-12 top-18 transform -rotate-90" type="range" v-model="playVolume" min="0" max="100">
              <span class="absolute top-3">{{ playVolume }}%</span>
              <a @click="silence">
                <svg v-if="playVolume > 66" class="absolute bottom-2 left-1.5 w-5 h-5 animate__animated animate__bounceIn"  viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg"><title/><path d="M126,192H56a8,8,0,0,0-8,8V312a8,8,0,0,0,8,8h69.65a15.93,15.93,0,0,1,10.14,3.54l91.47,74.89A8,8,0,0,0,240,392V120a8,8,0,0,0-12.74-6.43l-91.47,74.89A15,15,0,0,1,126,192Z" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M320,320c9.74-19.38,16-40.84,16-64,0-23.48-6-44.42-16-64" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/>
                  <path d="M368,368c19.48-33.92,32-64.06,32-112s-12-77.74-32-112" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M416,416c30-46,48-91.43,48-160S446,143,416,96" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/>
                </svg>
                <svg v-else-if="playVolume > 33" class="absolute bottom-2 left-1.5 w-5 h-5 animate__animated animate__bounceIn" viewBox="0 0 512 512"  xmlns="http://www.w3.org/2000/svg"><title/>
                  <path d="M157.65,192H88a8,8,0,0,0-8,8V312a8,8,0,0,0,8,8h69.65a16,16,0,0,1,10.14,3.63l91.47,75A8,8,0,0,0,272,392.17V119.83a8,8,0,0,0-12.74-6.44l-91.47,75A16,16,0,0,1,157.65,192Z" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M352,320c9.74-19.41,16-40.81,16-64,0-23.51-6-44.4-16-64" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M400,368c19.48-34,32-64,32-112s-12-77.7-32-112" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/>
                </svg>
                <svg v-else-if="playVolume > 0" class="absolute bottom-2 left-1.5 w-5 h-5 animate__animated animate__bounceIn" viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg"><title/>
                  <path d="M189.65,192H120a8,8,0,0,0-8,8V312a8,8,0,0,0,8,8h69.65a16,16,0,0,1,10.14,3.63l91.47,75A8,8,0,0,0,304,392.17V119.83a8,8,0,0,0-12.74-6.44l-91.47,75A16,16,0,0,1,189.65,192Z" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/><path d="M384,320c9.74-19.41,16-40.81,16-64,0-23.51-6-44.4-16-64" style="fill:none;stroke:#000;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"/>
                </svg>
                <svg v-else class="absolute bottom-2 left-1.5 w-5 h-5 animate__animated animate__bounceIn"  viewBox="0 0 512 512"  xmlns="http://www.w3.org/2000/svg"><title/><line style="fill:none;stroke:#000;stroke-linecap:round;stroke-miterlimit:10;stroke-width:32px" x1="416" x2="64" y1="432" y2="80"/>
                  <path d="M224,136.92v33.8a4,4,0,0,0,1.17,2.82l24,24a4,4,0,0,0,6.83-2.82V120.57a24.53,24.53,0,0,0-12.67-21.72,23.91,23.91,0,0,0-25.55,1.83,8.27,8.27,0,0,0-.66.51l-31.94,26.15a4,4,0,0,0-.29,5.92l17.05,17.06a4,4,0,0,0,5.37.26Z"/><path d="M224,375.08l-78.07-63.92A32,32,0,0,0,125.65,304H64V208h50.72a4,4,0,0,0,2.82-6.83l-24-24A4,4,0,0,0,90.72,176H56a24,24,0,0,0-24,24V312a24,24,0,0,0,24,24h69.76l91.36,74.8a8.27,8.27,0,0,0,.66.51A23.93,23.93,0,0,0,243.63,413,24.49,24.49,0,0,0,256,391.45V341.28a4,4,0,0,0-1.17-2.82l-24-24a4,4,0,0,0-6.83,2.82ZM125.82,336Z"/><path d="M352,256c0-24.56-5.81-47.88-17.75-71.27a16,16,0,0,0-28.5,14.54C315.34,218.06,320,236.62,320,256q0,4-.31,8.13a8,8,0,0,0,2.32,6.25l19.66,19.67a4,4,0,0,0,6.75-2A146.89,146.89,0,0,0,352,256Z"/><path d="M416,256c0-51.19-13.08-83.89-34.18-120.06a16,16,0,0,0-27.64,16.12C373.07,184.44,384,211.83,384,256c0,23.83-3.29,42.88-9.37,60.65a8,8,0,0,0,1.9,8.26l16.77,16.76a4,4,0,0,0,6.52-1.27C410.09,315.88,416,289.91,416,256Z"/><path d="M480,256c0-74.26-20.19-121.11-50.51-168.61a16,16,0,1,0-27,17.22C429.82,147.38,448,189.5,448,256c0,47.45-8.9,82.12-23.59,113a4,4,0,0,0,.77,4.55L443,391.39a4,4,0,0,0,6.4-1C470.88,348.22,480,307,480,256Z"/>
                </svg>
              </a>
            </div>
          </a><!--volume-->
          <a @click="changePlayType">
            <svg v-if="playType === 1" class="w-4 h-4 animate__animated animate__bounceIn" viewBox="0 0 30 29.99936" xmlns="http://www.w3.org/2000/svg"><title/><g data-name="Layer 2" id="Layer_2"><g id="Interface-Solid"><g id="interface-solid-multimedia-refresh-button-2"><path d="M28.75,17.77671a1.24991,1.24991,0,0,0-1.25,1.25,2.73109,2.73109,0,0,1-2.67578,2.77734H9.122l3.48254-3.57227a1.25,1.25,0,0,0-1.78906-1.74609L5.2627,22.181a1.25046,1.25046,0,0,0,0,1.7461l5.55273,5.69531a1.25,1.25,0,1,0,1.78906-1.7461L9.122,24.30405H24.8418c.00653,0,.0119-.00366.01837-.00366A5.23207,5.23207,0,0,0,30,19.02671,1.24991,1.24991,0,0,0,28.75,17.77671Z"/><path d="M20.87805,8.19467l-3.48254,3.57227A1.25,1.25,0,1,0,19.18457,13.513L24.7373,7.81772a1.25045,1.25045,0,0,0,0-1.74609L19.18457.37631a1.25,1.25,0,1,0-1.78906,1.7461l3.48254,3.57226H5.1582c-.00653,0-.0119.00367-.01837.00367A5.23207,5.23207,0,0,0,0,10.972a1.25,1.25,0,0,0,2.5,0A2.7311,2.7311,0,0,1,5.17578,8.19467Z"/></g></g></g>
            </svg>
            <svg v-else-if="playType === 2" class="w-4 h-4 animate__animated animate__bounceIn" viewBox="0 0 30 29.99936" xmlns="http://www.w3.org/2000/svg"><title/><g data-name="Layer 2" id="Layer_2"><g id="Interface-Solid"><g id="interface-solid-multimedia-refresh-button-3"><path d="M29.98035,2.15171a1.19758,1.19758,0,0,0-.04944-.24463,1.20863,1.20863,0,0,0-.08985-.23438c-.01574-.02978-.017-.06323-.0354-.09228a1.171,1.171,0,0,0-.09118-.09546,1.23677,1.23677,0,0,0-.583-.40845,1.19606,1.19606,0,0,0-.25208-.051A1.15361,1.15361,0,0,0,28.75.99936c-.03455,0-.06354.01685-.09735.01953a1.51168,1.51168,0,0,0-.47968.13965c-.02978.01563-.063.01685-.09192.03516l-1.5.95019A1.23783,1.23783,0,0,0,27.5,4.41489v7.33447a1.25,1.25,0,0,0,2.5,0v-9.5C30,2.21469,29.98309,2.18564,29.98035,2.15171Z"/><path d="M28.75,17.77671a1.24991,1.24991,0,0,0-1.25,1.25,2.73109,2.73109,0,0,1-2.67578,2.77734H9.122l3.48254-3.57227a1.25,1.25,0,0,0-1.78906-1.74609L5.2627,22.181a1.25046,1.25046,0,0,0,0,1.7461l5.55273,5.69531a1.25,1.25,0,1,0,1.78906-1.7461L9.122,24.30405H24.8418c.00653,0,.0119-.00366.01837-.00366A5.23207,5.23207,0,0,0,30,19.02671,1.24991,1.24991,0,0,0,28.75,17.77671Z"/><path d="M20.87805,8.19467l-3.48254,3.57227A1.25,1.25,0,1,0,19.18457,13.513L24.7373,7.81772a1.25045,1.25045,0,0,0,0-1.74609L19.18457.37631a1.25,1.25,0,1,0-1.78906,1.7461l3.48254,3.57226H5.1582c-.00653,0-.0119.00367-.01837.00367A5.23207,5.23207,0,0,0,0,10.972a1.25,1.25,0,0,0,2.5,0A2.7311,2.7311,0,0,1,5.17578,8.19467Z"/></g></g></g>
            </svg>
            <svg v-else class="w-4 h-4 animate__animated animate__bounceIn" viewBox="0 0 30.00437 20.21244" xmlns="http://www.w3.org/2000/svg"><title/><g data-name="Layer 2" id="Layer_2"><g id="Interface-Solid"><g id="interface-solid-multimedia-shuffle-button"><path d="M29.99359,16.07133a1.25067,1.25067,0,0,0-.08984-.44483,1.22462,1.22462,0,0,0-.158-.24121,1.19232,1.19232,0,0,0-.10608-.16211l-2.81006-2.86035a1.2853,1.2853,0,0,0-1.76953-.01953,1.25787,1.25787,0,0,0-.02,1.76953l.729.74024h-4.876a4.70287,4.70287,0,0,1-4.64307-4.75,7.20518,7.20518,0,0,0-7.14307-7.25H1.25a1.25,1.25,0,0,0,0,2.5H9.10693a4.70286,4.70286,0,0,1,4.64307,4.75,7.20506,7.20506,0,0,0,7.13849,7.24975l.00116.00025h4.87268l-.72229.72949a1.27089,1.27089,0,0,0,.02,1.78027,1.25351,1.25351,0,0,0,.87988.34961,1.241,1.241,0,0,0,.88965-.36914L29.63965,16.983a1.19393,1.19393,0,0,0,.10986-.168,1.21816,1.21816,0,0,0,.1543-.23608,1.25126,1.25126,0,0,0,.087-.43042c.00061-.01587.00922-.0293.00922-.04541C30,16.09183,29.9939,16.08256,29.99359,16.07133Z"/><path d="M17.6333,6.35307a1.24336,1.24336,0,0,0,.69971-.21485,4.55,4.55,0,0,1,2.56006-.78515h4.88208l-.72535.74023a1.16841,1.16841,0,0,0-.35986.87988,1.2453,1.2453,0,0,0,1.25,1.23926,1.25772,1.25772,0,0,0,.88965-.36914L29.63965,4.983a1.254,1.254,0,0,0,.17163-.25977,1.20855,1.20855,0,0,0,.09125-.13794,1.22462,1.22462,0,0,0-.26288-1.36206L26.82959.36283A1.2853,1.2853,0,0,0,25.06006.3433a1.24886,1.24886,0,0,0-.01026,1.76953l.72535.74024h-4.8855l-.002.00048a7.03891,7.03891,0,0,0-3.95606,1.21436,1.25013,1.25013,0,0,0,.70166,2.28516Z"/><path d="M11.667,14.06791a4.55,4.55,0,0,1-2.56006.78516H1.25a1.25,1.25,0,1,0,0,2.5H9.10693a7.04035,7.04035,0,0,0,3.96143-1.21485A1.25,1.25,0,0,0,11.667,14.06791Z"/></g></g></g>
            </svg>
          </a>
          <a @click="toggle">
            <svg class="w-5 h-5" viewBox="0 0 24 24"  xmlns="http://www.w3.org/2000/svg">
              <path d="M22,17 L22,19 L2,19 L2,17 L22,17 Z M22,11 L22,13 L2,13 L2,11 L22,11 Z M22,5 L22,7 L2,7 L2,5 L22,5 Z" fill-rule="evenodd"/>
            </svg>          
          </a>
        </div><!--end tools-->
        <div class="controller flex items-center space-x-3 pr-8">


          <audio @canplay="getDuration" @ended="songSwitch(true)" @timeupdate="setCurrentTime" ref="audioRef"></audio>

          <a class="hover:text-gray-400" @click="songSwitch(false)">
            <svg class="w-6 h-6 fill-current" viewBox="0 0 24 24"  xmlns="http://www.w3.org/2000/svg"><path d="M18.4,21.8 L6.4,12.8 C5.86666667,12.4 5.86666667,11.6 6.4,11.2 L18.4,2.2 C19.0592363,1.70557281 20,2.17595468 20,3 L20,21 C20,21.8240453 19.0592363,22.2944272 18.4,21.8 Z M18,5 L8.66666667,12 L18,19 L18,5 Z M6,22 L4,22 L4,2 L6,2 L6,22 Z" fill-rule="evenodd"/></svg>
          </a>

          <a @click="playOrPause" class="hover:text-gray-400">
            <div v-if="!play" class="">
              <svg class="w-6 h-6 animate__animated animate__bounceIn fill-current" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><polygon points="5 3 19 12 5 21 5 3"/></svg>
            </div>
            <div v-else class="">
              <svg class="w-6 h-6 animate__animated animate__bounceIn fill-current" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path d="M7,2 L9,2 C10.1045695,2 11,2.8954305 11,4 L11,20 C11,21.1045695 10.1045695,22 9,22 L7,22 C5.8954305,22 5,21.1045695 5,20 L5,4 C5,2.8954305 5.8954305,2 7,2 Z M15,2 L17,2 C18.1045695,2 19,2.8954305 19,4 L19,20 C19,21.1045695 18.1045695,22 17,22 L15,22 C13.8954305,22 13,21.1045695 13,20 L13,4 C13,2.8954305 13.8954305,2 15,2 Z M7,4 L7,20 L9,20 L9,4 L7,4 Z M15,4 L15,20 L17,20 L17,4 L15,4 Z" fill-rule="evenodd"/>
              </svg>
            </div>
          </a>

          <a @click="songSwitch(true)" class="hover:text-gray-400">
            <svg class="w-6 h-6 fill-current" viewBox="0 0 24 24"  xmlns="http://www.w3.org/2000/svg"><path d="M4,21 L4,3 C4,2.17595468 4.94076375,1.70557281 5.6,2.2 L17.6,11.2 C18.1333333,11.6 18.1333333,12.4 17.6,12.8 L5.6,21.8 C4.94076375,22.2944272 4,21.8240453 4,21 Z M6,19 L15.3333333,12 L6,5 L6,19 Z M20,22 L18,22 L18,2 L20,2 L20,22 Z" fill-rule="evenodd"/></svg>
          </a>
        </div>
      </div><!--end right-->
    </div>
  </footer>
</template>

<style scoped>

</style>