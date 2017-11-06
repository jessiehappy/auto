/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-06 09:52:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dealer_series`
-- ----------------------------
DROP TABLE IF EXISTS `dealer_series`;
CREATE TABLE `dealer_series` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dealerUsername` varchar(100) NOT NULL COMMENT '经销商用户名',
  `seriesId` bigint(20) NOT NULL COMMENT '销售车系ID',
  `guidePriceMin` int(11) NOT NULL COMMENT '最低指导价',
  `guidePriceMax` int(11) NOT NULL COMMENT '最高指导价',
  `priceStr` varchar(100) DEFAULT NULL COMMENT '经销商填写的商品价格字符串',
  `smallImg` varchar(100) NOT NULL COMMENT '车系小图 默认车系表小图',
  `bigImg` varchar(100) NOT NULL COMMENT '车系大图 默认车系表大图',
  `status` int(1) NOT NULL COMMENT '系列销售上架状态 0：上架 1：下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dealer_series
-- ----------------------------
