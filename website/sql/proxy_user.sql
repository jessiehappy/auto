/*
Navicat MySQL Data Transfer

Source Server         : my_local
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-27 17:48:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `proxy_user`
-- ----------------------------
DROP TABLE IF EXISTS `proxy_user`;
CREATE TABLE `proxy_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '小b-代理商表',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `username` varchar(200) NOT NULL COMMENT '用户名',
  `telephone` varchar(11) NOT NULL COMMENT '电话号码',
  `wechatId` varchar(200) DEFAULT NULL COMMENT '微信Id',
  `wechatName` varchar(200) DEFAULT NULL COMMENT '微信名称',
  `wechatFavicon` varchar(200) DEFAULT NULL COMMENT '微信头像',
  `token` varchar(200) DEFAULT NULL,
  `imgFile` varchar(200) NOT NULL COMMENT '认证图像存放文件夹名称（默认username）',
  `openId` varchar(200) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '认证状态  0:未认证  1:审核中  2:未通过  3:已通过',
  `dataStatus` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据状态 （0、正常   1、已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of proxy_user
-- ----------------------------
