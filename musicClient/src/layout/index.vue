<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-12 09:41:19
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-13 16:21:04
 * @FilePath: /musicClient/src/layout/index.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted } from 'vue'
import sideBar from './sideBar/sideBar.vue';
import mainIndex from './mainView/index.vue';
import playerIndex from '../components/core/player/index.vue';
import playList from 'coms/core/player/playList.vue'
import { userPlayInfo } from '@/store/userPlayInfo.ts'
import { userDataInfo } from '@/store/userDataInfo.ts'
import { storeToRefs } from 'pinia'
import { ElMessage } from 'element-plus'
import { UserSongListService } from '@/api/api.js'
import userEditView from 'coms/edit/userEditView.vue'

let playInfoStore = userPlayInfo()
let userDataStore = userDataInfo()

let { playSlider, player, addLoveView }  = storeToRefs(playInfoStore)
let { userDataEdit, userSongListData }  = storeToRefs(userDataStore)



document.addEventListener('mousedown', (e) => {
  
  let event = e || window.event
  let target = event.target

  let b = document.getElementById('slider_in')
  let c = document.getElementById('edit_in')

  //判断点击区域是否是滑块区域
  if(b != null && !b.contains(target)) {
    playSlider.value = false
  }

  if(c != null && !c.contains(target)) {
    userDataEdit.value = false
  }
})

const addMusicToUserList = (key) => {
  if (typeof userSongListData.value[key].songIdList === 'undefined') {
    userSongListData.value[key].createdTime = player.value.songId
  } else {
    let arr = userSongListData.value[key].songIdList.split(",");
    userSongListData.value[key].songIdList = ''
    for (let i = 0; i < arr.length; i++) {
      if (player.value.songId === arr[i]) {
        continue
      } else {
        if (i === 0) {
          userSongListData.value[key].songIdList = arr[i]
        } else {
          userSongListData.value[key].songIdList = userSongListData.value[key].songIdList + "," + arr[i] 
        }
      }
    }
    userSongListData.value[key].songIdList = userSongListData.value[key].songIdList + ',' + player.value.songId
  }
  UserSongListService.updateUserSongList(userSongListData.value[key]).then(res => {
    userSongListData.value = res.data.data
  })

  ElMessage({
          message: '添加成功',
          type: 'success',
        })
  addLoveView.value = false
}

</script>

<template>
  <div id="slider_out" class="relative">
    <div class="flex">
      <side-bar class="w-1/5"></side-bar>
      <main-index class="w-4/5"></main-index>
    </div>

    <div class="absolute bottom-0 right-0 w-4/5">
      <player-index></player-index>
    </div>

    <div id="edit_in" v-show="userDataEdit" class="transition-all absolute h-1/3 w-1/3 top-1/3 right-1/3 rounded-lg bg-opacity-50 backdrop-filter backdrop-grayscale backdrop-blur-md backdrop-contrast-200 bg-gray-200">
      <userEditView></userEditView>
    </div>

    <div id="slider_in" v-if="playSlider" class="overflow-y-auto transition-all absolute h-screen max-h-full right-0 top-0 w-1/5 bg-gray-200">
      <play-list></play-list>
    </div>

    <div v-if="addLoveView" class="transition-all bg-opacity-50 backdrop-filter backdrop-grayscale backdrop-blur-md backdrop-contrast-200 absolute h-1/5 w-1/3 top-1/3 right-1/3 rounded-lg bg-gray-200">
      <div class="flex  items-center mt-4 ">
        <span class="mx-auto">添加音乐到</span>
      </div>
      <div class="flex-warp h-2/3 mt-2 ml-10 space-y-2 overflow-y-auto">
        <div v-for="(item, key) in userSongListData" :key="key" class="inline-block mx-5 h-8 w-1/5 bg-red-300 rounded-md">
          <a @click="addMusicToUserList(key)" class="flex items-center h-full w-full">
            <span class="mx-auto">{{ item.name }}</span>
          </a>
        </div>
      </div>
    </div>

  </div>
  
  
</template>

<style scoped>

</style>