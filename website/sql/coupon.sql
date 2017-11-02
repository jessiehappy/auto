/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:31:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `coupon`
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `couponCode` varchar(100) NOT NULL COMMENT '优惠券编码',
  `couponName` varchar(100) NOT NULL COMMENT '优惠券名称',
  `telephone` varchar(100) NOT NULL COMMENT 'c端电话',
  `username` varchar(100) DEFAULT NULL COMMENT 'c端用户',
  `coupon` int(11) NOT NULL COMMENT '优惠金额',
  `createTime` datetime(6) NOT NULL COMMENT '创建时间',
  `endedTime` datetime(6) NOT NULL COMMENT '结束时间',
  `type` int(2) NOT NULL COMMENT '优惠券类型 0：汽车优惠券 1：other',
  `status` int(1) NOT NULL COMMENT '优惠券使用状态 0：未使用 1：已使用 2：已过期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coupon
-- ----------------------------
