<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-05 13:21:05
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-23 20:56:58
 * @FilePath: /musicManage/src/layout/content/tab03/addSongView.vue
 * @Description: null
-->
<script setup>
import { computed, ref, onMounted } from 'vue'
import { SongDataService } from '@/api/api.js'


const uploadRef = ref()

const submitUpload = () => {
  uploadRef.value.submit()
}

let singerId = ref('')
let tableData = ref([])
const addSong = ref(false)
const updateSong = ref(false)
//接收传过来的歌手ID
const props = defineProps({
  toSingerId: String
})

//定义歌曲内容
const song = ref({
  songId: null,
  singerId: null,
  name: null,
  album: null,
  lyric: null,
  url: null,
  photo: null,
  createdTime: null,
  updateTIme: null
})

const songCopy = ref({
  songId: null
})

onMounted( async() => {
  song.value.singerId = props.toSingerId
  songCopy.value.singerId = props.toSingerId
  SongDataService.getSongDataList(song.value).then( res => {
    tableData.value = res.data.data
  })
})

//切换歌手和添加歌曲页面
const emit = defineEmits(['on-click'])
let tab = ref(false)
const toSingerView = (tab) => {  
  emit('on-click', tab)
} 


const search = ref('')
const filterTableData = computed(() =>
  tableData.value.filter(
    (data) =>
      !search.value ||
      data.name.toLowerCase().includes(search.value.toLowerCase()) || 
      data.album.includes(search.value)
  )
)
const handleEdit = (index, row) => {
  console.log(index, row.url)
}
const handleDelete = (index, row) => {
  console.log(index, row)
}

function addSubmit() {
  SongDataService.addSongData(song.value).then( res => {
    SongDataService.getSongDataList(songCopy.value).then( res => {
      tableData.value = res.data.data
    })
  })
}

</script>

<template>
<div>
  <el-table border :data="filterTableData" style="width: 100%">
    <el-table-column align="center" label="歌名" prop="name" />
    <el-table-column align="center" label="专辑" prop="album" />
    <el-table-column align="center" label="歌曲" prop="album" #default="scope" >
      <el-upload
        ref="uploadRef"
        class="upload-demo"
        action="/api/song/updateSongFile"
        :data="scope.row"
        >
        <audio v-if="scope.row.url" controls>
          <source :src="scope.row.url" type="audio/mpeg">
        </audio>
        <el-button v-else type="primary">点击选择</el-button>
      </el-upload>
    </el-table-column>
    <el-table-column align="center" >
      <template #header>
        <el-input v-model="search" style="width: 30%" placeholder="Search" />
        <el-button style="margin: 0.5rem;" type="success" @click="addSong = true,updateSong = false" >添加歌曲</el-button>
        <el-button @click="toSingerView(tab)">返回</el-button>
      </template>
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
          >Edit</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)"
          >Delete</el-button
        >
      </template>
    </el-table-column>
  </el-table>

    <!-- 添加歌曲 -->
    <el-dialog v-model="addSong" title="填写歌曲信息">
    <el-form :model="song" :inline=true>
      <el-form-item label="歌曲名" >
        <el-input v-model="song.name" style="width: 200px" />
      </el-form-item>
      <el-form-item label="专辑" >
        <el-input v-model="song.album" style="width: 200px" />
      </el-form-item>
      <el-form-item style="margin-left: 40px;" label="歌词" >
        <el-input v-model="song.lyric" type="textarea" style="width: 200px" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addSong = false">取消</el-button>
        <el-button v-if="!updateSong" type="primary" @click="submitUpload,addSong = false,addSubmit()">
          添加
        </el-button>
        <el-button v-else type="primary" @click="updateSubmit(),addSong = false">
          更新
        </el-button>
      </span>
    </template>
  </el-dialog>

</div>
</template>

<style scoped>

</style>