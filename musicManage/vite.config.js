/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 10:27:09
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-01 11:00:31
 * @FilePath: /musicManage/vite.config.js
 * @Description: null
 */
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@" :path.resolve(__dirname, "src"),
      "coms": path.resolve(__dirname, "src/components"),
    }
  },
  server : {
    //设置客户端启动端口号
    host: 'localhost',
    port: 7777,
    open: false,
    //设置跨域代理
    proxy: {   
      '/api': {
        target: 'http://localhost:9999/',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '') // 不可以省略rewrite
      }
    }
  },
  css: {
    preprocessorOptions: {
        less: {
            modifyVars: {
                hack: `true; @import (reference) "${path.resolve("src/assets/css/base.less")}";`,
            },
            javascriptEnabled: true,
        },
    },
},
})
