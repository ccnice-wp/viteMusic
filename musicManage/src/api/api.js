/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-03-31 13:42:53
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-23 15:58:42
 * @FilePath: /musicManage/src/api/api.js
 * @Description: null
 */
import { request } from './axios'

/**
 * @description -封装User类型的接口方法
 */
export class UserService {       // 模块一
    /**
     * @description 用户登录
     * @param {string} username - 用户名
     * @return {HttpResponse} result
     */
    static async login1(params) {   // 接口一
        return request('/login',params, 'post')
    }
    static async login2(params) {   // 接口二
        return request('/login',params, 'post')
    }
    static async login3(params) {   // 接口三
        return request('/login',params, 'post')
    }
}

export class UserDataService {
  static async getUserDataList(params) {
    return request('/userData/getUserList', params, 'post')
  }
}

export class adminService {
  static async getInfo(params) {
    return request('admin/selectInfo', params, 'post')
  }
}

export class SongDataService {
  static async getSongDataList(params) {
    return request('/song/selectSong', params, 'post')
  }

  static async addSongData(params) {
    return request('/song/addSong', params, 'post')
  }

  static async updateSongData(params) {
    return request('/song/updateSong', params, 'post')
  }
}

export class SongListDataService {
  static async getSongListData(params) {
    return request('/songList/selectSongList', params, 'post')
  }
  static async addSongListData(params) {
    return request('/songList/addSongList', params, 'post')
  }
  static async updateSongListData(params) {
    return request('/songList/updateSongList', params, 'post')
  }
  static async getSongListMusic(params) {
    return request('/songList/getSongListMusic', params, 'post')
  }
  static async addSong(params) {
    return request('/songList/addSong', params, 'post')
  }
}

export class SingerDataService {
  static async getSingerDataList(params) {
    return request('/singer/getSingerList', params, 'post')
  }

  static async addSingerData(params) {
    return request('/singer/addSinger', params, 'POST')
  }

  static async updateSingerData(params) {
    return request('/singer/updateByPrimaryKey',params, 'post')
  }
}

export class landRelevant {     // 模块二
    /**
     * @description 获取地列表
     * @return {HttpResponse} result
     */
    static async landList(params) {
        return request('/comment/song/details',params, 'get')
    }
}