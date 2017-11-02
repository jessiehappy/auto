/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:32:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `auto_coupon_cancel_after_verification`
-- ----------------------------
DROP TABLE IF EXISTS `auto_coupon_cancel_after_verification`;
CREATE TABLE `auto_coupon_cancel_after_verification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '修改时间',
  `amount` int(11) NOT NULL COMMENT '核销金额 单位分',
  `username` varchar(100) NOT NULL COMMENT '经销商用户名',
  `autoCouponId` bigint(20) NOT NULL COMMENT '汽车券ID',
  `status` int(1) NOT NULL COMMENT '核销状态 0：未核销 1：核销成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auto_coupon_cancel_after_verification
-- ----------------------------
