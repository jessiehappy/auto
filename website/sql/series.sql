/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:35:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `series`
-- ----------------------------
DROP TABLE IF EXISTS `series`;
CREATE TABLE `series` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '系类名称',
  `creatTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '最后修改时间',
  `cId` bigint(20) NOT NULL COMMENT '商品分类ID',
  `smallImg` varchar(100) NOT NULL COMMENT '车系小图',
  `bigImg` varchar(100) NOT NULL DEFAULT '车系大图',
  `brandId` bigint(20) NOT NULL COMMENT '商品品牌id',
  `brandName` varchar(100) NOT NULL COMMENT '商品品牌名称',
  `minPrice` int(11) NOT NULL COMMENT '最低价格',
  `maxPrice` int(11) NOT NULL COMMENT '最高价格',
  `auditStatus` int(1) NOT NULL COMMENT '审核状态 0：审核通过 1：审核中 2：审核未通过',
  `saleStatus` int(1) NOT NULL COMMENT '是否上架 0：上架 1：下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of series
-- ----------------------------
