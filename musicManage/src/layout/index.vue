<script setup>
import { ref } from 'vue'
import Header from './header/index.vue'
import Content from './content/index.vue'
let tabNum = ref(1)
const tabSwitch = (number) => {
  tabNum.value = number
}


</script>

<template>
  <Header @on-click="tabSwitch"></Header>
  <Content :tabNum="tabNum"></Content>
</template>

<style scoped>

</style>