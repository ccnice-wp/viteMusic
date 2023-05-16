<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-01 09:57:56
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-15 19:01:17
 * @FilePath: /musicManage/src/layout/content/tab01/charts/chartOne.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { adminService } from '@/api/api.js'

// 用户年龄分层图

const playList = ref([])

onMounted( async () => {
  adminService.getInfo().then(res => {
    playList.value = res.data.data[1]
    initPie()
  })
})

const initPie = () => {
  const charts = echarts.init(document.querySelector('.initPie01'))
  charts.setOption({
    title: {
    text: '音乐前十排行榜',
    subtext: '',
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
  },
  // legend: { 
  //   left: 'center',
  //   top: 'bottom',
  //   data: [
  //     'rose 1',
  //     'rose 2',
  //     'rose 3',
  //     'rose 4',
  //     'rose 5',
  //     'rose 6',
  //     'rose 7',
  //     'rose 8'
  //   ]
  // },
  // toolbox: {
  //   show: true,
  //   feature: {
  //     mark: { show: true },
  //     dataView: { show: true, readOnly: false },
  //     restore: { show: true },
  //     saveAsImage: { show: true }
  //   }
  // },
  series: [
    {
      name: '音乐前十排行榜',
      type: 'pie',
      radius: [10, 100],
      center: ['50%', '50%'],
      roseType: 'area',
      itemStyle: {
        borderRadius: 2
      },
      data: [
        { value: parseInt(playList.value[0].photo), name: playList.value[0].name },
        { value: parseInt(playList.value[1].photo), name: playList.value[1].name },
        { value: parseInt(playList.value[2].photo), name: playList.value[2].name },
        { value: parseInt(playList.value[3].photo), name: playList.value[3].name },
        { value: parseInt(playList.value[4].photo), name: playList.value[4].name },
        { value: parseInt(playList.value[5].photo), name: playList.value[5].name },
        { value: parseInt(playList.value[6].photo), name: playList.value[6].name },
        { value: parseInt(playList.value[7].photo), name: playList.value[7].name },
        { value: parseInt(playList.value[8].photo), name: playList.value[8].name },
        { value: parseInt(playList.value[9].photo), name: playList.value[9].name },
      ]
    }
  ]
  })
}
</script>

<template>
  <!-- <span class="initPie01 bg-pink-100 w-1/2">
    
  </span> -->
  <div class="initPie01 bg-pink-100 w-5/12 inline-block border-2 border-gray-400 m-10">

  </div>

</template>

<style scoped lang="less">

.initPie01 {
  /* width: 500px; */
  height: 350px;
}

</style>