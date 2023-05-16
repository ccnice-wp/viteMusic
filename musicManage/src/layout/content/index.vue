<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 11:32:06
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-02 16:23:28
 * @FilePath: /musicManage/src/layout/content/index.vue
 * @Description: null
-->
<script setup>
import { ref } from 'vue'
import InfoView from './tab01/infoView.vue';
import UserView from './tab02/userView.vue';
import SingerView from './tab03/singerView.vue';
import SongListView from './tab04/songListView.vue';
const props = defineProps({
  tabNum: Number
})


</script>

<template>
  <div v-if="props.tabNum === 1" class="bg-gray-300">
    <InfoView></InfoView>
  </div>
  <div v-if="props.tabNum === 2" >
    <UserView></UserView>
  </div>
  <div v-if="props.tabNum === 3" >
    <SingerView></SingerView>
  </div>
  <div v-if="props.tabNum === 4" >
    <SongListView></SongListView>
  </div>
</template>

<style scoped>

</style>