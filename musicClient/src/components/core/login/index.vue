<!--
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-25 10:02:34
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-05-13 16:41:29
 * @FilePath: /musicClient/src/components/core/login/index.vue
 * @Description: null
-->
<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import 'element-plus/dist/index.css'
import { UserDataService, UserSongListService } from '@/api/api.js'
import { storeToRefs } from 'pinia'
import { userDataInfo } from '@/store/userDataInfo.ts'
import { useRouter } from 'vue-router'
const router = useRouter()

let userDataStore = userDataInfo()

let { userData } = storeToRefs(userDataStore)
const userSongList = ref({
  userId: null,
  name: null,
})

const swapLogin = ref(true)
const loginData = ref({
  name: '',
  password: ''
})
const registerData = ref({
  name: '',
  password1: '',
  password: ''
})

const userDataRes = ref({
  name: '',
  password: ''
})

const swapState = (value) => {
  swapLogin.value = value
}

const login = () => {

  if(loginData.value.name === '' || loginData.value.password === '') {
    ElMessage({
      message: '账号和密码不能为空',
      type: 'error',
    })
  } else {
    UserDataService.userDataLogin(loginData.value).then(res => {
      if(res.data.type === 'error') {
        ElMessage({
          message: '账号或密码错误',
          type: 'error',
        })
      } else {
        userData.value = res.data.data[0]

        ElMessage({
          message: '登录成功',
          type: 'success',
        })
        userSongList.value.userId = userData.value.userId
        UserSongListService.getUserSongList(userSongList.value).then(res => {
          if (res.data.data.length === 0) {
            userSongList.value.name = "我喜欢"
            UserSongListService.addUserSongList(userSongList.value).then(res => {

            })
          }
        })
        loginData.value.name = null
        loginData.value.password = null
        router.push('/')
      }
    })
  }
}

const register = () => {
  if(registerData.value.name === '' || registerData.value.password1 === '' || registerData.value.password === '') {
    ElMessage({
      message: '账号或密码不能为空',
      type: 'error',
    })
  } else if(registerData.value.password1 !== registerData.value.password) {
    ElMessage({
      message: '两次输入的密码不匹配',
      type: 'error',
    })
  } else {
    userDataRes.value.name = registerData.value.name
    userDataRes.value.password = registerData.value.password1
    UserDataService.addUserData(userDataRes.value).then(res => {
      if(res.data.type === 'error') {
        ElMessage({
          message: '该用户名已被注册',
          type: 'error',
        })
      } else {
        ElMessage({
          message: '注册成功',
          type: 'success',
        })


        registerData.value.name = ''
        registerData.value.password1 = ''
        registerData.value.password = ''

        swapLogin.value = !swapLogin.value
      }
    })
    
  }
}


</script>

<template>
  <div class="flex items-center justify-center h-screen w-screen bg-gradient-to-br from-yellow-300 via-red-350 to-pink-400">
    <div v-if="swapLogin" class="w-3/12 h-2/3 bg-gray-200 bg-opacity-50 rounded-md ring-1 container mx-auto flex my-8 space-y-2">
      <div class="w-full">
        <div class=" w-2/5 h-2/5 flex mx-auto">
          <img src="@/assets/logo.svg" alt="logo">
        </div>
        <div class="w-full">
          <div class="flex justify-center mt-4">
            <input type="text" v-model="loginData.name" class="rounded-lg h-8 px-4 py-1 border-0 outline-none bg-gray-200" placeholder="账号">
          </div>
          <div class="flex justify-center mt-4">
            <input type="password" v-model="loginData.password" class="rounded-lg h-8 px-4 py-1 border-0 outline-none bg-gray-200" placeholder="密码">
          </div>
        </div>
        <div class="w-full flex justify-center mt-14">
          <a class="w-full flex justify-center" @click="login">
            <div class="bg-blue-300 hover:bg-blue-400 flex items-center justify-center space-x-4 rounded-md w-2/5 h-10">
              <span>登</span>
              <span>录</span>
            </div>
          </a>
        </div>
        <div class="w-full flex justify-center mt-20">
          <a @click="swapState(false)">
            <span>去注册</span>
          </a>
        </div>
      </div>
    </div>
    <div v-else class="w-3/12 h-2/3 bg-gray-200 bg-opacity-50 rounded-md ring-1 container mx-auto flex my-8 space-y-2">
      <div class="w-full">
        <div class=" w-2/5 h-2/5 flex mx-auto">
          <img src="@/assets/logo.svg" alt="logo">
        </div>
        <div class="w-full">
          <div class="flex justify-center mt-4">
            <input type="text" v-model="registerData.name" class="rounded-lg h-8 px-4 py-1 border-0 outline-none bg-gray-200" placeholder="账号">
          </div>
          <div class="flex justify-center mt-4">
            <input type="password" v-model="registerData.password1" class="rounded-lg h-8 px-4 py-1 border-0 outline-none bg-gray-200" placeholder="密码">
          </div>
          <div class="flex justify-center mt-4">
            <input type="password" v-model="registerData.password" class="rounded-lg h-8 px-4 py-1 border-0 outline-none bg-gray-200" placeholder="确认密码">
          </div>
        </div>
        <div class="w-full flex justify-center mt-14">
          <a class="w-full flex justify-center" @click="register">
            <div class="bg-blue-300 hover:bg-blue-400 flex items-center justify-center space-x-4 rounded-md w-2/5 h-10">
              <span>注</span>
              <span>册</span>
            </div>
          </a>
        </div>
        <div class="w-full flex justify-center mt-10">
          <a @click="swapState(true)">
            <span>去登录</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>