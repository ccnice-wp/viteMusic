<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 17:38:50
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-03 20:42:52
 * @FilePath: /musicManage/src/layout/content/tab02/userView.vue
 * @Description: null
-->
<script  setup>
import { computed, ref, onMounted } from 'vue'
import { UserDataService } from '@/api/api.js'
import moment from 'moment';
 
let tableData = ref([])

onMounted( async() => {
  // 获取用户信息列表
  UserDataService.getUserDataList().then(res => {    
    tableData.value = res.data.data
  })
})

const test = () => {
  console.log(tableData.value[0].birth)
  console.log(moment(tableData.value[0].birth).format('YYYY-MM-DD'))
}

const filterTableData = computed(() =>
  tableData.value.filter(
    (data) => 
      (data.birth = moment(new Date(data.birth)).format('YYYY-MM-DD')) &&
      !search.value ||
      data.name.toLowerCase().includes(search.value.toLowerCase()) ||
      data.userId.includes(search.value.toString())
  ),
)

const search = ref('')

const handleEdit = (index, row) => {
  console.log(index, row)
}
const handleDelete = (index, row) => {
  console.log(index, row)
}

</script>

<template>
  <!-- <button @click="test">test</button> -->
  <div class="mt-5">
    <el-table border :data="filterTableData" style="width: 100%">
    <el-table-column align="center" label="用户ID" prop="userId"  />
    <el-table-column align="center" label="用户名" prop="name" />
    <el-table-column align="center" label="用户生日" sortable prop="birth" />
    <el-table-column align="center">
      <template #header>
        <el-input v-model="search" size="default" style="width: 60%" placeholder="搜索" />
      </template>
      <template #default="scope">
        <el-button type="warning" size="small" @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>

<style scoped>

</style>