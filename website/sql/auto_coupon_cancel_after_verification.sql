/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : auto

 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 11/06/2017 14:31:38 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `auto_coupon_cancel_after_verification`
-- ----------------------------
DROP TABLE IF EXISTS `auto_coupon_cancel_after_verification`;
CREATE TABLE `auto_coupon_cancel_after_verification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '修改时间',
  `amount` int(11) NOT NULL COMMENT '核销金额 单位分',
  `username` varchar(100) NOT NULL COMMENT '经销商用户名',
  `autoCouponId` bigint(20) NOT NULL COMMENT '汽车券ID',
  `proxyUsername` varchar(100) DEFAULT NULL COMMENT '代理人用户名',
  `coupon` int(11) NOT NULL COMMENT '优惠价格',
  `telephone` varchar(100) NOT NULL COMMENT '优惠券领电话',
  `cUsername` varchar(100) DEFAULT NULL COMMENT '消费用户名',
  `seriesId` bigint(20) NOT NULL COMMENT '车系ID',
  `status` int(1) DEFAULT NULL COMMENT '核销状态 0：未核销 1：核销成功 2:核销失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
