<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-01 09:58:19
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-15 18:54:24
 * @FilePath: /musicManage/src/layout/content/tab01/charts/chartTwo.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { adminService } from '@/api/api.js'
// 用户男女比例图
const genderData = ref({})

onMounted( async() => {
  adminService.getInfo().then(res => {
    genderData.value = res.data.data[0][1]
    genderData.value.male = parseInt(genderData.value.female)
    genderData.value.female = parseInt(genderData.value.male)
    genderData.value.unknown = parseInt(genderData.value.unknown)
    initPie()
  })
  
})

const initPie = () => {
  const charts = echarts.init(document.querySelector('.initPie'))
  charts.setOption({
    title: {
    text: '用户男女性别比例',
    subtext: '',
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  // legend: {
  //   orient: 'vertical',
  //   left: 'left'
  // },
  series: [
    {
      name: '用户性别比例',
      type: 'pie',
      radius: '50%',
      data: [
        { value: genderData.value.male, name: '男生' },
        { value: genderData.value.female, name: '女生' },
        { value: genderData.value.unknown, name: '未知' },
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
  })

}


</script>

<template>

  <!-- <span class="initPie02 bg-pink-50 w-1/2"></span> -->

  <div class="initPie  bg-pink-50 w-5/12 border-2 border-gray-400 inline-block m-10">

  </div>

</template>

<style scoped lang="less">

.initPie {
  /* width: 500px; */
  height: 350px;
}

</style>