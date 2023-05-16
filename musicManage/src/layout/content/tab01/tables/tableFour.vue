<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-01 12:12:16
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-15 18:52:48
 * @FilePath: /musicManage/src/layout/content/tab01/tables/tableFour.vue
 * @Description: null
-->
<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { gsap } from "gsap"
import { adminService } from '@/api/api.js'

onMounted(() => {
  adminService.getInfo().then(res => {
    numberAni(res.data.data[0][0].recentUsers)
  })
})

function numberAni(value) {
  number.value = value
}

const number = ref(0)
const tweened = reactive({
  number: 0
})

watch(
  number,
  (n) => {
    gsap.to(tweened, { duration: 1, number: Number(n) || 0 })
  }
)

</script>

<template>
  <div class="mt-5 mx-10 border-2 border-red-400 w-1/6 h-20 inline-flex items-center">
   
    <el-icon size="40" class="ml-8"><Top /></el-icon>


    <div class="ml-16">
      <a href="" class="block">{{ tweened.number.toFixed(0) }}</a>
      <a href="" class="block">月增长用户</a>
    </div>
 
    
  </div>

</template>

<style scoped>

</style>