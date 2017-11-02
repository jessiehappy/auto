/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-02 16:33:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dealer_account`
-- ----------------------------
DROP TABLE IF EXISTS `dealer_account`;
CREATE TABLE `dealer_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL COMMENT '代理商用户名',
  `realName` varchar(100) NOT NULL COMMENT '账户真实姓名',
  `idCard` varchar(100) NOT NULL COMMENT '身份证号',
  `alipayNumber` varchar(100) NOT NULL COMMENT '支付宝账户',
  `balance` int(11) NOT NULL COMMENT '余额 单位分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dealer_account
-- ----------------------------
