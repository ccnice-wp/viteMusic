/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : music

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 16/05/2023 09:00:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '管理员ID',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '管理员姓名',
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '管理员密码',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '用户评论ID',
  `user_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ID',
  `song_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '-1' COMMENT '歌曲ID',
  `song_list_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '-1' COMMENT '歌单ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '评论内容',
  `stars` int DEFAULT '0' COMMENT '点赞数',
  `type` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区分是歌曲评论(0)还是歌单评论(1)',
  `created_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `reply_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '评论回复表ID',
  `comment_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论表ID',
  `user_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '回复内容',
  `created_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for singer
-- ----------------------------
DROP TABLE IF EXISTS `singer`;
CREATE TABLE `singer` (
  `singer_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '歌手ID',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌手姓名',
  `gender` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌手性别',
  `photo` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌手图片url',
  `birth` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌手出生日期',
  `location` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌手地区',
  `introduction` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌手简介',
  `created_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`singer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song` (
  `song_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '歌曲ID',
  `singer_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌手ID',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌曲名',
  `album` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '专辑名',
  `lyric` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '歌词',
  `url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌曲url',
  `photo` int DEFAULT NULL COMMENT '歌曲封面url',
  `created_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌曲创建时间',
  `update_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌曲更新时间',
  PRIMARY KEY (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for song_list
-- ----------------------------
DROP TABLE IF EXISTS `song_list`;
CREATE TABLE `song_list` (
  `song_list_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '歌单ID',
  `song_id_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '歌单中歌曲ID列表',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌单名',
  `photo` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌单封面url',
  `introduction` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '歌单介绍',
  `style` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '风格标签',
  `created_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`song_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_data
-- ----------------------------
DROP TABLE IF EXISTS `user_data`;
CREATE TABLE `user_data` (
  `user_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名\n',
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户密码',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '用户性别，默认0，1男，2女',
  `birth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户生日',
  `location` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户地区',
  `photo` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户头像url',
  `introduction` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户介绍',
  `created_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_song_list
-- ----------------------------
DROP TABLE IF EXISTS `user_song_list`;
CREATE TABLE `user_song_list` (
  `user_song_list_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '用户歌单列表ID',
  `user_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ID',
  `song_id_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '用户歌单歌曲列表',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户歌单名',
  `photo` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户歌单封面url',
  `introduction` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户歌单介绍',
  `created_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户歌单创建时间',
  PRIMARY KEY (`user_song_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
