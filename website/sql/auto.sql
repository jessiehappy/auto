/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : auto

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-30 08:59:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `auto_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `auto_coupon`;
CREATE TABLE `auto_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `couponId` bigint(20) NOT NULL COMMENT '优惠券ID',
  `c_telephone` varchar(100) NOT NULL COMMENT 'c端用户电话号码',
  `dealerCouponId` bigint(20) NOT NULL COMMENT '经销商优惠券ID',
  `proxyCouponId` bigint(20) NOT NULL COMMENT '代理人优惠券ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auto_coupon
-- ----------------------------

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

-- ----------------------------
-- Table structure for `brand`
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandKey` varchar(2) NOT NULL COMMENT '品牌首字母',
  `brandLogoUrl` varchar(100) NOT NULL COMMENT '品牌logoUrl',
  `brandName` varchar(100) NOT NULL COMMENT '品牌名称',
  `brandStatus` int(1) NOT NULL COMMENT '品牌状态 0：正常使用 1：已停用',
  `creatTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of brand
-- ----------------------------

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cName` varchar(100) NOT NULL COMMENT '类别名称',
  `lev` int(1) NOT NULL COMMENT '类目级别 1：一级类目；2：二级类目；3：三级类目',
  `parentId` bigint(20) NOT NULL COMMENT '父ID',
  `sortNumber` int(2) NOT NULL COMMENT '排序 如：进口车为1，国产车位2',
  `creatTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '修改时间',
  `status` int(1) NOT NULL COMMENT '是否启用 0：未启用 1：已起用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------

-- ----------------------------
-- Table structure for `category_brand`
-- ----------------------------
DROP TABLE IF EXISTS `category_brand`;
CREATE TABLE `category_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandId` bigint(20) NOT NULL COMMENT '品牌ID',
  `brandStatus` int(1) NOT NULL COMMENT '品牌当前类目下是否有效 0：无效 1：有效',
  `sortNumber` int(11) NOT NULL COMMENT '排序',
  `createTime` datetime(6) NOT NULL COMMENT '创建时间',
  `modifiedTime` datetime(6) NOT NULL COMMENT '修改时间',
  `secondLevId` bigint(20) NOT NULL COMMENT '平台二级目录ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category_brand
-- ----------------------------

-- ----------------------------
-- Table structure for `coupon`
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `couponCode` varchar(100) NOT NULL COMMENT '优惠券编码',
  `username` varchar(100) NOT NULL COMMENT 'c端用户',
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

-- ----------------------------
-- Table structure for `dealer_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `dealer_coupon`;
CREATE TABLE `dealer_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dealerUsername` varchar(100) NOT NULL COMMENT '经销商用户名',
  `dealerSeriesId` bigint(20) NOT NULL COMMENT '经销商车系ID',
  `seriesId` bigint(20) NOT NULL COMMENT '车系ID',
  `couponNum` int(11) DEFAULT NULL COMMENT '优惠券数量当优惠券没有生成时，显示"未生成"  否则，显示数量',
  `commission` int(11) NOT NULL COMMENT '佣金 单位分',
  `startTime` datetime(6) NOT NULL COMMENT '优惠券生效时间',
  `finishedTime` datetime(6) NOT NULL COMMENT '优惠券失败时间',
  `lock` int(1) NOT NULL COMMENT '是否锁定 0：锁定 1：未锁定',
  `lockTime` datetime(6) NOT NULL COMMENT '锁定时间',
  `status` int(1) NOT NULL COMMENT '优惠券状态 ：0-有效 1-无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dealer_coupon
-- ----------------------------

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
  `smallImg` varchar(100) NOT NULL COMMENT '车系小图 默认车系表小图',
  `bigImg` varchar(100) NOT NULL COMMENT '车系大图 默认车系表大图',
  `status` int(1) NOT NULL COMMENT '系列销售上架状态 0：上架 1：下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dealer_series
-- ----------------------------

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
