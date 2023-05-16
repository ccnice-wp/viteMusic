<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-12 15:44:06
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-13 17:28:08
 * @FilePath: /musicClient/src/layout/mainView/index.vue
 * @Description: null
-->
<script setup>
import { ref } from 'vue'
import navBar from './navBar/index.vue';
import mainCenter from './mainCenter/index.vue';
import searchListData from './navBar/searchList.vue'
import rankingList from './navBar/rankingList.vue';
import recentlyPlayed from './navBar/recentlyPlayed.vue';
import userSongList from './navBar/userSongList.vue'
import { storeToRefs } from 'pinia'
import { searchDataInfo } from '@/store/searchDataInfo.ts'
import { userDataInfo } from '../../store/userDataInfo';

let searchDataStore = searchDataInfo()
let userDataStore = userDataInfo()

let { searchList, showSearchList } = storeToRefs(searchDataStore)
let { userViewIndex } = storeToRefs(userDataStore)

</script>

<template>

  <div class="flex-wrap">
    <div>
      <nav-bar></nav-bar>
    </div>
    <div v-if="userViewIndex === 1" class=""> <!--推荐视图-->
      <div v-show="!showSearchList">
        <main-center></main-center>
      </div>
      <div v-if="showSearchList">
        <search-list-data></search-list-data>
      </div>
    </div>

    <div v-else-if="userViewIndex === 2"><!--排行榜-->
      <ranking-list></ranking-list>
    </div>

    <div v-else-if="userViewIndex === 3"><!--我喜欢-->
      <user-song-list></user-song-list>
    </div>

    <div v-else-if="userViewIndex === 4"><!--最近播放-->
      <recently-played></recently-played>
    </div>

    <div  v-else><!--用户自建歌单-->
      <user-song-list></user-song-list>
    </div>
   
  </div>

</template>

<style scoped>

</style>