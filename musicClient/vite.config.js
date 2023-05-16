/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-29 10:42:04
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-16 13:24:54
 * @FilePath: /musicClient/vite.config.js
 * @Description: null
 */
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

/**
 * 解决使用music-metadata-browser时浏览器无法获取Buffer的报错
 * Uncaught (in promise) ReferenceError: Buffer is not defined
 * 原因是在页面中使用了Buffer，但是浏览器中JS没有Buffer对象，它是存在Node中。
 */

import { nodePolyfills } from 'vite-plugin-node-polyfills'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    nodePolyfills({
      // Whether to polyfill `node:` protocol imports.
      protocolImports: true,
    }),
  ],
  // 配置路径别名
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "src"),
      "coms": path.resolve(__dirname, "src/components"),
    }
  },
  server: {
    //设置客户端启动端口号
    host: 'localhost',
    port: 8888,
    open: false,    //是否自动打开浏览器
    //设置跨域代理
    proxy: {
      '/api': {
        target: 'http://localhost:9999',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')   //不可省略rewrite
      }
    }
  }
})
