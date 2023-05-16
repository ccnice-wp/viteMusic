<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 11:32:12
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-15 15:53:56
 * @FilePath: /musicManage/src/layout/header/index.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted,  } from 'vue'
import { landRelevant } from '@/api/api.js'

onMounted(() => {
  landRelevant.landList().then(res => {
    console.log(res);
  })
  console.log(`the component is now mounted.`)
})

const emit = defineEmits(['on-click'])
let tab = ref(1)
const tabSwitch = (num) => {  
  emit('on-click', num)
  tab.value = num
} 

</script>

<template>
  <div class="h-16 bg-gray-800 flex text-gray-300 px-5">
    <a href="" class="mr-5">
      <img src="/public/vite.svg" alt="" class="h-10 mt-3"/>
    </a>
    <span class="flex items-center">后台管理</span>
    <nav class="ml-20 flex items-center">
      <button @click="tabSwitch(1)" :class="{ tabColor: tab === 1 }" class="px-5 py-2 mx-1 rounded-xl hover:bg-gray-600">信息统计</button>
      <button @click="tabSwitch(2)" :class="{ tabColor: tab === 2 }" class="px-5 py-2 mx-1 rounded-xl hover:bg-gray-600">用户管理</button>
      <button @click="tabSwitch(3)" :class="{ tabColor: tab === 3 }" class="px-5 py-2 mx-1 rounded-xl hover:bg-gray-600">歌手管理</button>
      <button @click="tabSwitch(4)" :class="{ tabColor: tab === 4 }" class="px-5 py-2 mx-1 rounded-xl hover:bg-gray-600">歌单管理</button>
    </nav>
    <div class="group ml-auto relative flex ai">
      <img src="/src/assets/images/userLogo.png" alt="" class="rounded-full h-9 mt-3.5">
      
      <div class="absolute z-50 bg-white text-gray-700 text-sm top-14 right-0 w-48 shadow-lg ring-1 ring-black ring-opacity-5 py-2 rounded-md scale-0 group-hover:scale-100 duration-300 origin-top-right">
        <a href="#" class="block px-4 py-2 hover:bg-gray-100 ">个人资料</a>
        <a href="#" class="block px-4 py-2 hover:bg-gray-100 ">设置</a>
        <a href="#" class="block px-4 py-2 hover:bg-gray-100 ">退出</a>
      
      </div>
    </div>
    
  </div>

</template>

<style scoped>
.tabColor {
  background-color: black;
}
</style>