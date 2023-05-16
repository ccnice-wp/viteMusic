<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 17:39:05
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-23 16:45:58
 * @FilePath: /musicManage/src/layout/content/tab03/singerView.vue
 * @Description: null
-->
<script setup>
import { computed, ref, onMounted, onBeforeUpdate } from 'vue'
import { SingerDataService } from '@/api/api.js'
import AddSongView from './addSongView.vue'
import moment from 'moment';

//图片上传
const imageUrl = ref('')

const handleAvatarSuccess = (response, uploadFile) => {
  SingerDataService.getSingerDataList().then(res => {
    tableData.value = res.data.data
    console.log(tableData.value)
  })
}

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}


//表单主体
let tableData = ref([])
const addSinger = ref(false)
const updateSinger = ref(false)
const addSong = ref(false)

onMounted( async() => {
  SingerDataService.getSingerDataList().then(res => {
    tableData.value = res.data.data
  })
})

//添加相应歌手的歌曲

const toSingerView = (tab) => {
  addSong.value = tab
}

//重制signer中的数据
const remakeSinger = () => {
  for(let key in singer.value) {
    singer.value[key] = ''
  }
}

//添加歌手信息
function addSubmit() {
  SingerDataService.addSingerData(singer.value).then(res => {
    console.log(res.data.message)
    SingerDataService.getSingerDataList().then(res => {
    tableData.value = res.data.data
    })
  })  
}

//梗更新歌手信息
function updateSubmit() {
  SingerDataService.updateSingerData(singer.value).then(res => {
    console.log(res.data.message)
    SingerDataService.getSingerDataList().then(res => {
    tableData.value = res.data.data
    })
  })  
}

const singer = ref({
  singerId: '',
  name: '',
  gender: '',
  birth: '',
  location: '',
  introduction: '',
  createdTime: ''
})

const search = ref('')

const filterTableData = computed(() =>
  tableData.value.filter(
    (data) =>
      (data.birth = moment(new Date(data.birth)).format('YYYY-MM-DD')) && 
      !search.value ||
      data.name.toLowerCase().includes(search.value.toLowerCase()) ||
      data.singerId.includes(search.value)
  )
)

const handleEdit = (index, row) => {
  singer.value = row
  console.log(index, row.singerId)
}

//往添加歌曲页面传送对应的歌手ID
const toSingerId = ref('')
const handleDelete = (index, row) => {
  toSingerId.value = row.singerId
  console.log(index, row.singerId)
}

</script>

<template>
  <div v-if="!addSong">
    <el-table scrollbar-always-on max-height="650" border algin="center"  :data="filterTableData" style="width: 100%">
    <el-table-column align="center" label="歌手ID" prop="singerId" width="200"/>
    <el-table-column align="center" label="图片" prop="photo" width="100" #default="scope">
      <!-- <el-avatar shape="square" :size="50" :src="squareUrl"> -->
      <el-upload
        class="avatar-uploader"
        action="/api/upload/SingerImage"
        :data="scope.row"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"

      >
      <img v-if="scope.row.photo" :src="scope.row.photo" class="avatar" />
      <el-icon v-else class="avatar-uploader-icon"><Avatar /></el-icon>
     </el-upload>
    <!-- </el-avatar>  -->
    </el-table-column>
    <el-table-column align="center" label="姓名" prop="name" width="100" />
    <el-table-column align="center" label="性别" prop="gender" width="80"/>
    <el-table-column align="center" label="生日" prop="birth" :sortable="true" width="120"/>
    <el-table-column align="center" label="地区" prop="location" width="100" />
    <el-table-column align="center" label="简介" prop="introduction" :show-overflow-tooltip="true" />
    <el-table-column align="center">
      <template #header>
        <el-input v-model="search" style="width: 50%;" size="default" placeholder="Search" />
        <el-button style="margin: 0.5rem;" type="success" @click="addSinger = true,updateSinger = true,remakeSinger()">添加歌手</el-button>
      </template>
      <template #default="scope">
        <el-button size="small"  @click="handleEdit(scope.$index, scope.row),addSinger = true,updateSinger = false"
          >编辑</el-button
        >
        <el-button
          size="small"
          type="success"
          @click="handleDelete(scope.$index, scope.row),addSong = true"
          >歌曲列表</el-button
        >
      </template>
    </el-table-column>
  </el-table>

  <!-- 添加歌手 -->
  <el-dialog v-model="addSinger" title="添加歌手信息">
    <el-form :model="singer" :inline=true>
      <el-form-item label="姓名" >
        <el-input v-model="singer.name" style="width: 200px" />
      </el-form-item>
      <el-form-item label="性别" >
        <el-select v-model="singer.gender" placeholder="点击选择">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>
      <el-form-item label="生日">
          <el-date-picker
            v-model="singer.birth"
            type="date"
            label="Pick a date"
            placeholder="点击选择"
            style="width: 100%"
          />
        </el-form-item>
      <el-form-item label="地区" >
        <el-input v-model="singer.location" style="width: 200px" />
      </el-form-item>
      <el-form-item label="介绍" >
        <el-input v-model="singer.introduction" type="textarea" style="width: 474px"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addSinger = false">取消</el-button>
        <el-button v-if="updateSinger" type="primary" @click="addSubmit(),addSinger = false">
          添加
        </el-button>
        <el-button v-else type="primary" @click="updateSubmit(),addSinger = false">
          更新
        </el-button>
      </span>
    </template>
  </el-dialog>
  </div>

  <!-- 添加歌曲 -->
  <div v-else>
    <AddSongView @on-click="toSingerView" :toSingerId="toSingerId"></AddSongView>
  </div>

</template>

<style scoped>

.avatar-uploader .avatar {
  width: 50px;
  height: 50px;
  display: block;
}

</style>