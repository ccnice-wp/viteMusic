/*
 * @Author: ccnice whl0808_work@163.com
 * @Date: 2023-04-15 22:37:40
 * @LastEditors: ccnice whl0808_work@163.com
 * @LastEditTime: 2023-04-16 12:26:43
 * @FilePath: /musicClient/src/utils/audioToImage.js
 * @Description: null
 */
// 从网络上获取 MP3 文件的二进制数据
import * as musicMetadata from 'music-metadata-browser'

export async function audioToImage(mp3Url) {
  const metadata = await musicMetadata.fetchFromUrl(mp3Url);
	console.log("歌曲信息",metadata);
	const pictures = metadata.common.picture
  const pictureUrl = URL.createObjectURL(new Blob([pictures[0].data], { type: pictures[0].format }))
  return pictureUrl
}