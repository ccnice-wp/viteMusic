<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-12 09:37:14
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-13 17:53:48
 * @FilePath: /musicClient/src/layout/sideBar/sideBar.vue
 * @Description: null
-->
<script setup>
import { ref, h, nextTick, onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { storeToRefs  } from 'pinia'
import { UserSongListService } from '@/api/api.js'
import { userDataInfo } from '../../store/userDataInfo';
import { ElMessage } from 'element-plus'


let userDataStore = userDataInfo()

let { userViewIndex, userData, userSongListOne, userSongListData } = storeToRefs(userDataStore)

const router = useRouter()

const userSongList = ref({
  userId: null,
  name: null,
})

// const userSongListData = ref([])

const refInput = ref(null)
const showInput = ref(false)
const newEnjoyValue = ref("歌单")
const showUserSongList = ref([])

onMounted(() => {
  if (!(userData.value.userId === null || userData.value.userId === '')) {
    userSongList.value.userId = userData.value.userId
    UserSongListService.getUserSongList(userSongList.value).then(res => {
      userSongListData.value = res.data.data
      showUserSongList.value = userSongListData.value.slice()
      for (let i = 0; i < showUserSongList.value.length; i++) {
        if (showUserSongList.value[i].name === "我喜欢") {
          showUserSongList.value.splice(i, 1)
        }
      }
    })
  }
  
})

const toTest = () => {
  router.push({
    path: '/test'
  })
}

const tabViewIndex = (value) => {
  if(value !== 1 && value !== 2 && value !== 4) {
    if(userData.value.name === null || userData.value.name === '') {
      ElMessage({
          message: '请先登录',
          type: 'error',
        })
    } else if (value === 3) {
      console.log(userSongListData.value[0].name)
      for (let i = 0; userSongListData.value.length; i++) {
        if (userSongListData.value[i].name === "我喜欢") {
          userSongListOne.value = userSongListData.value[i]
          break
        }
      }
      userViewIndex.value = value
    } else if (value > 4){
      userSongListOne.value = userSongListData.value[value - 4]
      userViewIndex.value = value
    } else {
      userViewIndex.value = value
    }
  } else {
    userViewIndex.value = value
  }
}

const addLikeTab = () => {
  if(userData.value.name === null || userData.value.name === '') {
    ElMessage({
          message: '请先登录',
          type: 'error',
        })
  } else {
    showInput.value = true

    newEnjoyValue.value = '歌单' + (userSongListData.value.length)

    nextTick(() => {
      refInput.value.focus()
    })
  }
}

//当用户点击新建歌单输入框其他的区域时，让新建歌单消失，取消新建
document.addEventListener('mousedown', (e) => {
  
  let event = e || window.event
  let target = event.target

  let b = document.getElementById('inputNewEnjoy')
  // let c = document.getElementById('edit_in')

  //判断点击区域是新建歌单内部
  if(b != null && !b.contains(target)) {
    showInput.value = false
  }  
  
})

//添加新的用户歌单
const createEnjoyList = () => {

  userSongList.value.userId = userData.value.userId
  userSongList.value.name = newEnjoyValue.value

  UserSongListService.addUserSongList(userSongList.value).then(res => {
    userSongList.value.name = null
    UserSongListService.getUserSongList(userSongList.value).then(res => {
      userSongListData.value = res.data.data
      showUserSongList.value = userSongListData.value.slice()
      for (let i = 0; i < showUserSongList.value.length; i++) {
        if (showUserSongList.value[i].name === "我喜欢") {
          showUserSongList.value.splice(i, 1)
        }
      }
    })
  })
  showInput.value = false
  ElMessage({
          message: '新的歌单已成功添加',
          type: 'success',
        })
}

</script>

<template>
  <div class="side-bar w-1/5 h-screen bg-gray-100 space-y-16">
    <div class="title pt-10 text-center">
      <a class="flex items-center ml-10 space-x-4">
        <img class="h-8 w-8" src="@/assets/logo.svg" alt="logo">
        <span class="font-semibold text-xl">Vite 音乐</span>
      </a>
    </div><!--end title-->

    <div class="up-bar space-y-4">
      <span class="text-gray-400 pl-10">在线音乐</span>

      <a @click="tabViewIndex(1)" :class="{ bgEmerald: userViewIndex === 1 , hoverColor: userViewIndex !== 1 }" class="flex text-gray-500 items-center mx-6 py-2 pl-4 pr-6 rounded-md">
        <div class="w-4 h-4 rounded-full flex justify-around">
          <svg class="w-4 h-4 mt-0.5 " xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" >
            <path stroke-linecap="round" stroke-linejoin="round" d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
          </svg>
        </div>
        <span class="ml-2">推荐</span>
      </a>

      <a @click="tabViewIndex(2)" :class="{ bgEmerald: userViewIndex === 2 , hoverColor: userViewIndex !== 2 }" class="flex text-gray-500 items-center mx-6 py-2 pl-4 pr-6 rounded-md">
        <svg class="w-4 h-4 " xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" >
          <path stroke-linecap="round" stroke-linejoin="round" d="M9 9l10.5-3m0 6.553v3.75a2.25 2.25 0 01-1.632 2.163l-1.32.377a1.803 1.803 0 11-.99-3.467l2.31-.66a2.25 2.25 0 001.632-2.163zm0 0V2.25L9 5.25v10.303m0 0v3.75a2.25 2.25 0 01-1.632 2.163l-1.32.377a1.803 1.803 0 01-.99-3.467l2.31-.66A2.25 2.25 0 009 15.553z" />
        </svg>
        <span class="ml-2">排行榜</span>
      </a>
    </div> <!--end up-bar-->

    <div class="center-bar space-y-4">
      <span class="text-gray-400 pl-10">我的音乐</span>

      <a @click="tabViewIndex(3)" :class="{ bgEmerald: userViewIndex === 3 , hoverColor: userViewIndex !== 3 }" class="flex text-gray-500 items-center mx-6 py-2 pl-4 pr-6 rounded-md">
        <svg class="w-4 h-4 " xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" >
          <path stroke-linecap="round" stroke-linejoin="round" d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12z" />
        </svg>

        <span class="ml-2">我喜欢</span>
      </a>
      <a @click="tabViewIndex(4)" :class="{ bgEmerald: userViewIndex === 4 , hoverColor: userViewIndex !== 4 }" class="flex text-gray-500 items-center mx-6 py-2 pl-4 pr-6 rounded-md">
        <svg class="w-4 h-4  " xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        <span class="ml-2">最近播放 </span>
      </a>
    </div> <!--end center-bar-->



    <div class="down-bar space-y-2">

      <div class="flex items-center">
        <span class="text-gray-400 pl-10">创建的歌单</span>
        <a @click="addLikeTab" class="pl-10 hover:text-gray-800 text-gray-400" >
          <svg class="w-4 h-4 fill-current" viewBox="0 0 128 128" xmlns="http://www.w3.org/2000/svg"><title/>
            <path d="M64,0a64,64,0,1,0,64,64A64.07,64.07,0,0,0,64,0Zm0,122a58,58,0,1,1,58-58A58.07,58.07,0,0,1,64,122Z"/><path d="M90,61H67V38a3,3,0,0,0-6,0V61H38a3,3,0,0,0,0,6H61V90a3,3,0,0,0,6,0V67H90a3,3,0,0,0,0-6Z"/>
          </svg>
        </a>
      </div>
      <div id="inputNewEnjoy" v-if="showInput">
        <input ref="refInput" v-model="newEnjoyValue" @keyup.enter="createEnjoyList" type="text" class="mx-6 pl-4 text-gray-700 rounded-xl border-0 outline-none bg-gray-200 placeholder-gray-400 px-3 py-1" placeholder="添加歌单">
      </div>
      <div style="max-height: 200px;" class="overflow-y-auto space-y-1" v-if="userData.name !== null && userData.name !== ''">
        <div v-for="(item, key) in showUserSongList" :key="key">
          <a @click="tabViewIndex(5 + key)" :class="{ bgEmerald: userViewIndex === 5 + key , hoverColor: userViewIndex !== 5 + key }" class="flex text-gray-500 items-center mx-6 py-1 pl-4 pr-6 rounded-md ">
            <span class="ml-2">{{ item.name }}</span>
          </a>
        </div>
      </div>
      
    </div> <!--end down-bar-->

  </div>
</template>

<style scoped>

.bgEmerald {
  --tw-bg-opacity: 1;
  background-color: rgb(52 211 153 / var(--tw-bg-opacity));
}

.hoverColor:hover {
  --tw-bg-opacity: 1;
  background-color: rgb(156 163 175 / var(--tw-bg-opacity));
}
</style>