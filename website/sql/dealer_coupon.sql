/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-06 09:52:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dealer_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `dealer_coupon`;
CREATE TABLE `dealer_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dealerUsername` varchar(100) NOT NULL COMMENT '经销商用户名',
  `dealerSeriesId` bigint(20) NOT NULL COMMENT '经销商车系ID',
  `seriesId` bigint(20) NOT NULL COMMENT '车系ID',
  `titleName` varchar(100) DEFAULT NULL COMMENT '汽车商品名称',
  `priceStr` varchar(100) NOT NULL COMMENT '经销商填写的商品价格字符串',
  `couponNum` int(11) NOT NULL COMMENT '优惠券数量',
  `commission` int(11) NOT NULL COMMENT '佣金 单位分',
  `startTime` datetime(6) NOT NULL COMMENT '优惠券生效时间',
  `finishedTime` datetime(6) NOT NULL COMMENT '优惠券失效时间',
  `lock` int(1) NOT NULL COMMENT '是否锁定 0：锁定 1：未锁定',
  `lockTime` datetime(6) DEFAULT NULL COMMENT '锁定时间',
  `status` int(1) NOT NULL COMMENT '优惠券状态 ：0-有效 1-无效',
  `verifiedNum` int(100) NOT NULL DEFAULT '0' COMMENT '优惠券核销人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dealer_coupon
-- ----------------------------
