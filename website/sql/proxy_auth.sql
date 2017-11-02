/*
Navicat MySQL Data Transfer

Source Server         : my_local
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-27 17:48:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `proxy_auth`
-- ----------------------------
DROP TABLE IF EXISTS `proxy_auth`;
CREATE TABLE `proxy_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '代理商认证表',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime DEFAULT NULL COMMENT '修改时间',
  `username` varchar(200) NOT NULL COMMENT '用户名',
  `openId` varchar(200) NOT NULL,
  `realName` varchar(200) NOT NULL COMMENT '真实姓名',
  `idNo` varchar(50) NOT NULL COMMENT '身份证号码',
  `frontImg` varchar(200) NOT NULL COMMENT '身份证正面照',
  `backImg` varchar(200) DEFAULT NULL COMMENT '身份证背面照',
  `qualification` varchar(200) DEFAULT NULL COMMENT '资格证',
  `latitude` decimal(11,7) NOT NULL COMMENT '纬度',
  `longitude` decimal(11,7) NOT NULL COMMENT '经度',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '认证状态   0-未认证  1-审核中  2-未通过  3-已通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of proxy_auth
-- ----------------------------
