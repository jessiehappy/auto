/*
Navicat MySQL Data Transfer

Source Server         : my_local
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-27 17:47:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dealer_auth`
-- ----------------------------
DROP TABLE IF EXISTS `dealer_auth`;
CREATE TABLE `dealer_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '大B-经销商认证表',
  `username` varchar(200) NOT NULL COMMENT '用户名',
  `telephone` varchar(20) NOT NULL COMMENT '电话号码',
  `address` varchar(200) NOT NULL COMMENT '公司地址',
  `company` varchar(200) NOT NULL COMMENT '公司名称',
  `businessLicence` varchar(200) NOT NULL COMMENT '营业执照',
  `longitude` decimal(11,7) NOT NULL COMMENT '经度',
  `latitude` decimal(11,7) NOT NULL COMMENT '纬度',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '认证状态 0-未认证  1-审核中 2-未通过 3-已通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dealer_auth
-- ----------------------------
