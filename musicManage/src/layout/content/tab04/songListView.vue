<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 17:39:55
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-23 16:49:19
 * @FilePath: /musicManage/src/layout/content/tab04/songListView.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted, computed } from 'vue'
import { SongListDataService } from '@/api/api.js'
import AddSongView from './addSongView.vue'
import moment from 'moment';

//图片上传
const imageUrl = ref('')

const handleAvatarSuccess = (response, uploadFile) => {
  SongListDataService.getSongListData().then(res => {
    tableData.value = res.data.data
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
const addSongList = ref(false)
const updateSongList = ref(false)
const addSong = ref(false)

onMounted( async() => {
  SongListDataService.getSongListData().then(res => {
    tableData.value = res.data.data
  })

})

//添加相应歌手的歌曲
const toSongListView = (tab) => {
  addSong.value = tab
}

//重制songList中的数据
const remakeSongList = () => {
  for(let key in songList.value) {
    songList.value[key] = ''
  }
}

//添加歌手信息
function addSubmit() {

  SongListDataService.addSongListData(songList.value).then(res => {
    SongListDataService.getSongListData().then(res => {
      tableData.value = res.data.data
    })
  })
}

//梗更新歌手信息
function updateSubmit() {

  SongListDataService.updateSongListData(songList.value).then(res => {
    SongListDataService.getSongListData().then(res => {
      tableData.value = res.data.data
    })
  })

}

const songList = ref({
  songListId: '',
  name: '',
  style: '',
  introduction: '',
})




const search = ref('')

const filterTableData = computed(() =>
  tableData.value.filter(
    (data) =>
      !search.value ||
      data.name.toLowerCase().includes(search.value.toLowerCase()) ||
      data.songListId.includes(search.value)
  )
)

const handleEdit = (index, row) => {
  songList.value = row
}

//往添加歌曲页面传送对应的歌单ID
const toSongListId = ref('')
const handleDelete = (index, row) => {
  toSongListId.value = row.songListId
}


</script>

<template>
  <div v-if="!addSong">
    <el-table scrollbar-always-on max-height="650" border algin="center"  :data="filterTableData" style="width: 100%">
    <el-table-column align="center" label="歌单ID" prop="songListId" width="200"/>
    <el-table-column align="center" label="图片" prop="photo" width="100" #default="scope">
      <!-- <el-avatar shape="square" :size="50" :src="squareUrl"> -->
      <el-upload
        class="avatar-uploader"
        action="/api/upload/SongListImage"
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
    <el-table-column align="center" label="名称" prop="name" width="100" />
    <el-table-column align="center" label="类别" prop="style" width="180"/>
    <el-table-column align="center" label="简介" prop="introduction" width="600" :show-overflow-tooltip="true" />
    <el-table-column align="center">
      <template #header>
        <el-input v-model="search" style="width: 50%;" size="default" placeholder="Search" />
        <el-button style="margin: 0.5rem;" type="success" @click="addSongList = true,updateSongList = true,remakeSongList()">添加歌单</el-button>
      </template>
      <template #default="scope">
        <el-button size="small"  @click="handleEdit(scope.$index, scope.row),addSongList = true,updateSongList = false"
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

  <!-- 添加歌单 -->
  <el-dialog v-model="addSongList" title="填写歌单信息">
    <el-form :model="songList" :inline=true>
      <el-form-item label="名称" >
        <el-input v-model="songList.name" style="width: 200px" />
      </el-form-item>
      <el-form-item label="类别" >
        <el-input v-model="songList.style" style="width: 200px" />
      </el-form-item>
      <el-form-item label="介绍" >
        <el-input v-model="songList.introduction" type="textarea" style="width: 474px"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addSongList = false">取消</el-button>
        <el-button v-if="updateSongList" type="primary" @click="addSubmit(),addSongList = false">
          添加
        </el-button>
        <el-button v-else type="primary" @click="updateSubmit(),addSongList = false">
          更新
        </el-button>
      </span>
    </template>
  </el-dialog>
  </div>

    <!-- 添加歌曲 -->
  <div v-else >
    <AddSongView @on-click="toSongListView" :toSongListId="toSongListId"></AddSongView>
  </div>

</template>

<style scoped>

</style>