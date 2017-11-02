/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:33:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(100) NOT NULL COMMENT '类别名称',
  `lev` int(1) NOT NULL COMMENT '类目级别 1：一级类目；2：二级类目；3：三级类目',
  `parentId` bigint(20) NOT NULL COMMENT '父ID',
  `sortNumber` int(2) NOT NULL COMMENT '排序',
  `creatTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '修改时间',
  `status` int(1) NOT NULL COMMENT '是否启用 0：未启用 1：已起用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
