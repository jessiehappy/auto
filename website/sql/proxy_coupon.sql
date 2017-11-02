/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:35:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `proxy_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `proxy_coupon`;
CREATE TABLE `proxy_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `proxyUsername` varchar(100) NOT NULL COMMENT '代理人',
  `coupon` int(11) NOT NULL COMMENT '代理人优惠价格设置',
  `dealerCouponId` bigint(20) NOT NULL COMMENT '经销商优惠券ID',
  `dealerUsername` varchar(100) NOT NULL COMMENT '经销商用户名',
  `proxyCommission` int(11) NOT NULL COMMENT '总佣金经销商设置的佣金单位分',
  `seriesId` bigint(20) NOT NULL COMMENT '车系ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of proxy_coupon
-- ----------------------------
