<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-22 19:40:49
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-24 09:49:23
 * @FilePath: /musicClient/src/components/core/player/playList.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import * as musicMetadata from 'music-metadata-browser'
import { userPlayInfo } from '@/store/userPlayInfo.ts'

let playInfoStore = userPlayInfo()

const images = ref([])
const playDuration = ref([])

let { playList, player, playIndex } = storeToRefs(playInfoStore)

const playListSum = ref(playList.value.length)
onMounted(async () => {
  if(playList.value.length > 0) {
    for(let i = 0; i < playList.value.length; i++) {

      //获取专辑封面
      let metadata = await musicMetadata.fetchFromUrl(playList.value[i].url);
      let pictures = metadata.common.picture
      let ansUrl = URL.createObjectURL(new Blob([pictures[0].data], { type: pictures[0].format }))
      images.value.push(ansUrl) 

      //获取音乐时长
      let duration
      let m
      let s
      
      // let url = URL.createObjectURL(file);
      //经测试，发现audio也可获取视频的时长
      //生成一个html标签
      let audioElement = new Audio(playList.value[i].url);
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
  }
})

//获取音乐的时长
const getDuration = (url) => {
  let duration;
  let m
  let s
}

//播放音乐
const test = (key) => {
  playIndex.value = key
}

</script>

<template>
  <div class=" mt-2 max-h-full">
    <div class="tittle ml-2 font-semibold text-lg ">播放列表</div>
    <div class="flex ml-2 justify-between items-center">
      <div class="text-sm">共{{ playListSum }}首歌曲</div>
      <div class="flex items-center space-x-2 mr-2">
        <div></div>
        <div></div>
      </div>
    </div>
    <div class="text-sm mt-2">
      <div class="pl-2 pb-1 pt-1 mb-1 hover:bg-gray-400 hover:rounded-sm" v-for="(item, key) in playList " :key="key">
        <a @click="test(key)">
          <div :class="{ textColor: key == playIndex }" class="flex justify-between items-end">
            <div class="flex">
              <div class="w-10 h-10 bg-purple-400 rounded-sm ">
                <img v-if="images[key] != ''" class="rounded-sm" :src="images[key]" alt="">
              </div>
              <div class="flex-wrap ml-2 ">
                <div>{{ item.name }}</div>
                <div>{{ item.singer }}</div>
              </div>
            </div>
            <div class="pr-2">
              {{ playDuration[key] }}
            </div>
          </div>
        </a>
      </div>
    </div>
  </div>
</template>

<style scoped>

.textColor {
  color: #801dae;
}

</style>