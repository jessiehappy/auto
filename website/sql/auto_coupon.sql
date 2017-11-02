/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:32:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `auto_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `auto_coupon`;
CREATE TABLE `auto_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `couponId` bigint(20) NOT NULL COMMENT '优惠券ID',
  `couponUrl` varchar(100) NOT NULL COMMENT ' 汽车优惠券二维码图片地址',
  `c_telephone` varchar(100) NOT NULL COMMENT 'c端用户电话号码',
  `dealerCouponId` bigint(20) NOT NULL COMMENT '经销商优惠券ID',
  `proxyCouponId` bigint(20) NOT NULL COMMENT '代理人优惠券ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auto_coupon
-- ----------------------------
