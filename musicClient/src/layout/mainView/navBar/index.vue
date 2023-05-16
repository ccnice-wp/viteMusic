<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-12 15:26:25
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-09 17:36:06
 * @FilePath: /musicClient/src/layout/mainView/navBar/index.vue
 * @Description: null
-->
<script setup>
import { ref, watch } from 'vue'
import { storeToRefs }  from 'pinia'
import { h } from 'vue'
import { ElMessage } from 'element-plus'
import { userDataInfo } from '@/store/userDataInfo.ts'
import { useRouter } from 'vue-router'
import 'element-plus/dist/index.css'
import { SongDataService, SingerDataService } from '@/api/api.js'
import { searchDataInfo } from '@/store/searchDataInfo.ts'

let searchDataStore = searchDataInfo()
let { searchList, showSearchList, searchValue } = storeToRefs(searchDataStore)

const router = useRouter()

let userDataStore = userDataInfo()

let { userData, userDataEdit, userViewIndex } = storeToRefs(userDataStore)

const editUserData = () => {
  userDataEdit.value = true
}

const login = () => {
  if(userData.value.name === null) {
    router.push('/login')
  } else {

    userViewIndex.value = 1

    ElMessage({
      message: '注销成功',
      type: 'success',
    })
    userData.value.name = null
    userData.value.photo = null
  }
}

const search = ref("")
const testValue = ref([])

const songValue = ref({
  name: null,
})

const singerValue = ref({
  singerId: null
})

const searchResult = ref([])

const toSearchList = (value1, value2) => {
  if(searchResult.value[value1][0].singerId) {
    singerValue.value.singerId = searchResult.value[value1][value2].singerId
    SongDataService.getSongDataList(singerValue.value).then(res => {
      searchList.value = res.data.data
    })
    searchValue.value = searchResult.value[value1][value2].name
  } else {
    searchList.value = []
    searchList.value.push(searchResult.value[value1][value2])
    searchValue.value = searchResult.value[value1][value2].name
  }

  search.value = ''
  searchResult.value = []

  showSearchList.value = true
}

watch(search,(newValue) => {

  searchResult.value = []

  if(search.value !== '' && search.value !== ' ') {
    
    songValue.value.name = search.value.toLowerCase()
    SongDataService.getSongDataList(songValue.value).then(res => {
      // console.log("test1",res.data.data)
      if(res.data.data.length > 0) {
        searchResult.value.push(res.data.data)
      }
    })

    SingerDataService.getSingerDataList(songValue.value).then(res => {
      // console.log("test2",res.data.data)
      if(res.data.data.length > 0) {
        searchResult.value.push(res.data.data)
      }
    })
  }
})



</script>

<template>
  <nav class="justify-between pt-10 px-4 py-4 flex items-center ">
    <div class="flex text-white items-center space-x-4">
      <div class="relative">
        <div class="absolute top-0 left-0 flex items-center h-full">
          <div class="border rounded text-xs px-2 mr-2 text-gray-500">
            <svg class="w-4 h-4" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" >
              <path stroke-linecap="round" stroke-linejoin="round" d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" />
            </svg>
          </div>
        </div>
        <input type="text" v-model="search" :class="{ none: searchResult.length !== 0 }"  class="pl-10 text-gray-700 rounded-xl border-0 outline-none bg-gray-200 placeholder-gray-400 w-72 px-3 py-1" placeholder="搜索音乐">
        <div class="absolute transition duration-500 ease-in-out top-8 max-h-40 overflow-y-auto  w-full text-gray-500 bg-gray-200 rounded-xl rounded-t-none">
          <div v-for="(items, key1) in searchResult" :key="key1" class="">
            <div v-for="(item, key2) in items" :key="key2" class="h-8 pl-10 py-1 hover:bg-gray-300 rounded-md">
              <a @click="toSearchList(key1,key2)">
                <div class="h-full w-full">
                  <span>{{ item.name }}</span>            
                </div>
              </a>
            </div> 
          </div>
        </div>
      </div> 
    </div>

    <div class="flex items-center space-x-4">
      <a >
        <div class="group relative w-7 h-7 rounded-full border border-gray-700">

          <img class="h-full rounded-full" v-if="userData.photo !== null" :src="userData.photo" alt="头像">

          <svg v-else class="top-0 left-0 h-full" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" >
            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z" />
          </svg>

          <div class="absolute z-50 bg-white text-gray-700 text-sm top-7 right-4 w-48 shadow-lg ring-1 ring-black ring-opacity-5 py-2 rounded-md scale-0 group-hover:scale-100 duration-300 origin-top-right">
            <a v-if="userData.name !== null" @click="editUserData" class="block px-4 py-2 hover:bg-gray-100 ">个人资料</a>
            <a @click="login" class="block px-4 py-2 hover:bg-gray-100 ">
              <span v-if="userData.name === null">去登录</span>
              <span v-else>注销</span>
            </a>
          </div>

        </div>
      </a>
    </div>
  </nav>
</template>

<style scoped>

.none {
  border-bottom-right-radius: 0px;
  border-bottom-left-radius: 0px;
}

</style>