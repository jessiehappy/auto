/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:35:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `payment`
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '修改时间',
  `username` varchar(100) NOT NULL COMMENT '账单用户名',
  `fromUsername` varchar(100) NOT NULL COMMENT '付款人',
  `toUsername` varchar(100) NOT NULL COMMENT '收款人',
  `amount` int(11) NOT NULL COMMENT '账单总额',
  `paymentCost` int(11) NOT NULL COMMENT '支付金额 单位分',
  `balanceCost` int(11) NOT NULL COMMENT '余额支付金额 单位分',
  `paymentType` int(1) NOT NULL COMMENT '账单类型 0：充值',
  `description` varchar(100) NOT NULL COMMENT '支付单描述',
  `paidlnfo` varchar(100) NOT NULL COMMENT '支付成功信息',
  `status` int(1) NOT NULL COMMENT '支付单状态 0：未支付 1：支付中 2：支付成功 3：取消支付',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payment
-- ----------------------------
