<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 17:38:05
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-15 18:12:42
 * @FilePath: /musicManage/src/layout/content/tab01/infoView.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted } from 'vue'
import chartOne from './charts/chartOne.vue';
import chartTwo from './charts/chartTwo.vue';
import tableFour from './tables/tableFour.vue';
import tableOne from './tables/tableOne.vue';
import tableThree from './tables/tableThree.vue';
import tableTwo from './tables/tableTwo.vue';


</script>


<template>
  <div class="text-center">
    <tableOne></tableOne>
    <tableTwo></tableTwo>
    <tableThree></tableThree>
    <tableFour></tableFour>
  </div>
  <div class="text-center">
    <chartOne></chartOne>
    <chartTwo></chartTwo>
  </div>
</template>

<style scoped>

</style>