<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-25 20:49:43
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-26 14:41:06
 * @FilePath: /musicClient/src/components/edit/userEditView.vue
 * @Description: null
-->
<script setup>
import { ref, onMounted } from 'vue'
import 'element-plus/dist/index.css'
import { ElMessage } from 'element-plus'
import { storeToRefs } from 'pinia'
import { userDataInfo } from '@/store/userDataInfo.ts'
import { UserDataService } from '@/api/api.js'

let userDataStore = userDataInfo()

let { userData, userDataEdit } = storeToRefs(userDataStore)

const userDataForm = ref({})
const userDataImage = ref({
  userId: '',
  photo: ''
})

onMounted(() => {
  userDataForm.value = userData.value
  userDataImage.value.userId = userData.value.userId
  userDataImage.value.photo = userData.value.photo

})

const onSubmit = () => {
  UserDataService.userDataUpdate(userDataForm.value).then(res => {
    UserDataService.getUserDataList(userDataForm.value).then(res => {
      userData.value = res.data.data[0]
      userDataForm.value = userData.value

      ElMessage({
          message: '修改成功',
          type: 'success',
      })


      userDataEdit.value = false

    })
  })


}

// 图片上传成功后获取新图片的url
const handleAvatarSuccess = (response, uploadFile) => {
  UserDataService.getUserDataList(userDataImage.value).then(res => {
    userData.value = res.data.data[0]
    userDataForm.value = userData.value
  })
}

// 上传前查看上传的是否是图片以及图片大小是符合要求
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' &&  rawFile.type !== 'image/png') {
    ElMessage.error('图片格式错误')
    return false
  } else if (rawFile.size / 1024 / 1024 > 200) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}

</script>

<template>
  <div class="h-full w-full">
    <div class="up w-full h-1/2 flex">
      <div class="w-2/5 h-3">
        <div class="mt-4 ml-14 w-28 h-28 bg-purple-300 rounded-full">
          <el-upload
            class="avatar-uploader"
            action="/api/upload/userDataImage"
            :show-file-list="false"
            :data="userDataImage"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img class="h-full w-full rounded-full"  v-if="userDataForm.photo !== null" :src="userDataForm.photo"  />
            <svg class="h-full w-full text-gray-500" v-else xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" >
              <path stroke-linecap="round" stroke-linejoin="round" d="M12 16.5V9.75m0 0l3 3m-3-3l-3 3M6.75 19.5a4.5 4.5 0 01-1.41-8.775 5.25 5.25 0 0110.233-2.33 3 3 0 013.758 3.848A3.752 3.752 0 0118 19.5H6.75z" />
            </svg>

          </el-upload>
        </div>
      </div>
      <div class="w-3/5  pt-6 pl-4">
        <el-form :model="userDataForm" :inline="true">
          <el-form-item label="性别">
            <el-input v-model="userDataForm.gender"></el-input>
          </el-form-item>
          <el-form-item label="家乡">
           <el-input v-model="userDataForm.location"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="down h-1/2 w-full pt-4 px-6">
      <div class="h-2/3">
        <el-form :model="userDataForm">
          <el-form-item label="个人简介">
            <el-input v-model="userDataForm.introduction" type="textarea" />
          </el-form-item>
        </el-form>
      </div>
      <div class="h-1/3 flex justify-end pr-6">
        <el-form>
          <el-form-item size="default">
            <el-button type="primary" @click="onSubmit">确认</el-button>
          </el-form-item>
        </el-form>
      </div>
      
    </div>
  </div>
  
</template>

<style scoped>

</style>