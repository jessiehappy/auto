/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:33:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `category_brand`
-- ----------------------------
DROP TABLE IF EXISTS `category_brand`;
CREATE TABLE `category_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandId` bigint(20) NOT NULL COMMENT '品牌ID',
  `brandStatus` int(1) NOT NULL COMMENT '品牌当前类目下是否有效 0：无效 1：有效',
  `sortNumber` int(11) NOT NULL COMMENT '排序',
  `createTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '修改时间',
  `secondLevId` bigint(20) DEFAULT NULL COMMENT '平台二级目录ID',
  `thirdLevId` bigint(20) DEFAULT NULL COMMENT '平台三级目录ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category_brand
-- ----------------------------
