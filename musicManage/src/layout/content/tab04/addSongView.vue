<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-05 13:21:05
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-23 20:24:13
 * @FilePath: /musicManage/src/layout/content/tab04/addSongView.vue
 * @Description: null
-->
<script setup>
import { computed, ref, onMounted } from 'vue'
import { SongListDataService } from '@/api/api.js'


const uploadRef = ref()

const submitUpload = () => {
  songList.value.songListId = props.toSongListId
  SongListDataService.getSongListMusic(songList.value).then(res => {
    console.log(res.data.data)
    tableData.value = res.data.data
  })
}

let tableData = ref([])
const addSong = ref(false)
const updateSong = ref(false)
//接收传过来的歌单ID
const props = defineProps({
  toSongListId: String
})

//定义歌单内容
const songList = ref({
  songListId: null,
  singerIdList: null,
  name: null,
  photo: null,
  createdTime: null,
  updateTIme: null
})

const song = ref({
  songId: '965547568136343552',
  name: '967356887601405952'
})

//重制song中的数据
const remakeSong = () => {
  for(let key in song.value) {
    song.value[key] = ''
  }
}


onMounted( async() => {

  songList.value.songListId = props.toSongListId
  console.log(songList.value.songListId)
  SongListDataService.getSongListMusic(songList.value).then(res => {
    console.log(res.data.data)
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
  song.value.name = props.toSongListId
  console.log(song.value)
  SongListDataService.addSong(song.value).then(res => {

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
        <el-button style="margin: 0.5rem;" type="success" @click="addSong = true,updateSong = false,remakeSong()" >添加歌曲</el-button>
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
    <el-dialog v-model="addSong" title="添加歌曲">
    <el-form :model="song" :inline=true>
      <el-form-item  label="歌曲ID" >
        <el-input v-model="song.songId" type="textarea" style="width: 600px" placeholder="输入歌曲的ID以“,”隔开" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addSong = false">取消</el-button>
        <el-button v-if="!updateSong" type="primary" @click="submitUpload(),addSong = false,addSubmit()">
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