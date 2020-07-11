/*
Navicat MySQL Data Transfer

Source Server         : itdr_sd
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : itdr_sd16

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2019-04-11 14:12:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for neuedu_cart
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_cart`;
CREATE TABLE `neuedu_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `checked` int(11) DEFAULT NULL COMMENT '是否选择，1=已勾选，0=未勾选',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_cart
-- ----------------------------
INSERT INTO `neuedu_cart` VALUES ('125', '23', '10004', '1', '1', '2018-11-28 17:00:41', '2018-11-28 17:00:43');
INSERT INTO `neuedu_cart` VALUES ('128', '22', '10000', '1', '1', '2018-11-29 13:09:55', '2018-11-29 15:58:26');
INSERT INTO `neuedu_cart` VALUES ('129', '23', '10001', '1', '1', '2018-11-28 17:00:41', '2018-11-28 17:00:43');
INSERT INTO `neuedu_cart` VALUES ('130', '23', '10002', '1', '1', '2018-11-28 17:00:41', '2018-11-28 17:00:43');
INSERT INTO `neuedu_cart` VALUES ('131', '23', '10003', '1', '1', '2018-11-28 17:00:41', '2018-11-28 17:00:43');
INSERT INTO `neuedu_cart` VALUES ('132', '32', '10000', '2', '1', '2019-04-10 10:05:04', '2019-04-10 10:05:04');

-- ----------------------------
-- Table structure for neuedu_category
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_category`;
CREATE TABLE `neuedu_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类Id,当pareng_id=0,说明是根节点，一级类别',
  `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常，2-已废弃',
  `sort_order` int(4) DEFAULT NULL COMMENT '排序编号，同类展示顺序，数值相等则自然排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_category
-- ----------------------------
INSERT INTO `neuedu_category` VALUES ('1', '0', '是', '1', null, '2018-11-28 00:52:07', '2018-11-28 00:52:10');
INSERT INTO `neuedu_category` VALUES ('2', '1', '的', '1', null, '2018-11-28 00:54:17', '2018-11-28 00:54:20');
INSERT INTO `neuedu_category` VALUES ('3', '1', '是', '1', null, '2018-11-28 00:54:32', '2018-11-28 00:54:35');
INSERT INTO `neuedu_category` VALUES ('4', '2', '是', '1', null, '2018-11-28 00:54:55', '2018-11-28 00:54:57');
INSERT INTO `neuedu_category` VALUES ('5', '3', '是', '1', null, '2018-11-28 00:55:19', '2018-11-28 00:55:22');
INSERT INTO `neuedu_category` VALUES ('6', '5', '是', '1', null, '2018-11-28 00:55:34', '2018-11-28 00:55:37');
INSERT INTO `neuedu_category` VALUES ('7', '0', 'y ', '1', null, '2018-11-28 10:25:07', '2018-11-28 10:25:09');

-- ----------------------------
-- Table structure for neuedu_order
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_order`;
CREATE TABLE `neuedu_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(11) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `shipping_id` int(11) DEFAULT NULL,
  `payment` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额，单位元，保留两位小数',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付类型，1-在线支付',
  `postage` int(10) DEFAULT NULL COMMENT '运费，单位是元',
  `status` int(10) DEFAULT NULL COMMENT '订单状态：0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_order
-- ----------------------------
INSERT INTO `neuedu_order` VALUES ('11', '29', '1543647331297', '2', '4.00', '1', '0', '0', null, null, null, null, '2018-12-01 14:55:31', '2018-12-01 14:55:31');
INSERT INTO `neuedu_order` VALUES ('12', '29', '1544058244925', '2', '2.00', '1', '0', '10', null, null, null, null, '2018-12-06 09:04:04', '2018-12-06 09:04:04');
INSERT INTO `neuedu_order` VALUES ('13', '29', '1544058688327', '2', '2.00', '1', '0', '10', null, null, null, null, '2018-12-06 09:11:28', '2018-12-06 09:11:28');
INSERT INTO `neuedu_order` VALUES ('14', '29', '1544084214204', '2', '5.00', '1', '0', '10', null, null, null, null, '2018-12-06 16:16:54', '2018-12-06 16:16:54');
INSERT INTO `neuedu_order` VALUES ('15', '29', '111', '2', '30.00', '1', '0', '10', null, null, null, null, '2018-12-06 17:31:44', '2018-12-06 17:31:44');
INSERT INTO `neuedu_order` VALUES ('16', '29', '1544106756242', '2', '90.00', '1', '0', '10', null, null, null, null, '2018-12-06 22:32:36', '2018-12-06 22:32:36');
INSERT INTO `neuedu_order` VALUES ('17', '29', '1544108518356', '2', '90.00', '1', '0', '10', null, null, null, null, '2018-12-06 23:01:58', '2018-12-06 23:01:58');
INSERT INTO `neuedu_order` VALUES ('18', '29', '1544108730074', '2', '90.00', '1', '0', '10', null, null, null, null, '2018-12-06 23:05:30', '2018-12-06 23:05:30');
INSERT INTO `neuedu_order` VALUES ('19', '29', '1544109601845', '2', '90.00', '1', '0', '10', null, null, null, null, '2018-12-06 23:20:01', '2018-12-06 23:20:01');
INSERT INTO `neuedu_order` VALUES ('20', '29', '1544109803770', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-06 23:23:23', '2018-12-06 23:23:23');
INSERT INTO `neuedu_order` VALUES ('21', '29', '1544110018468', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-06 23:26:58', '2018-12-06 23:26:58');
INSERT INTO `neuedu_order` VALUES ('22', '29', '1544111407014', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-06 23:50:07', '2018-12-06 23:50:07');
INSERT INTO `neuedu_order` VALUES ('23', '29', '1544114781328', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-07 00:46:21', '2018-12-07 00:46:21');
INSERT INTO `neuedu_order` VALUES ('24', '29', '1544115250443', '2', '50.00', '1', '0', '20', '2018-12-07 00:55:01', null, null, null, '2018-12-07 00:54:10', '2018-12-07 00:54:10');
INSERT INTO `neuedu_order` VALUES ('25', '29', '1544365237813', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-09 22:20:37', '2018-12-09 22:20:37');
INSERT INTO `neuedu_order` VALUES ('26', '29', '1544366642514', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-09 22:44:02', '2018-12-09 22:44:02');
INSERT INTO `neuedu_order` VALUES ('27', '29', '1544366675071', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-09 22:44:35', '2018-12-09 22:44:35');
INSERT INTO `neuedu_order` VALUES ('28', '29', '1544367815453', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-09 23:03:35', '2018-12-09 23:03:35');
INSERT INTO `neuedu_order` VALUES ('29', '29', '1544368367955', '2', '50.00', '1', '0', '10', null, null, null, null, '2018-12-09 23:12:47', '2018-12-09 23:12:47');
INSERT INTO `neuedu_order` VALUES ('30', '29', '1544403688559', '2', '50.00', '1', '0', '20', '2018-12-10 09:05:06', null, null, null, '2018-12-10 09:01:28', '2018-12-10 09:05:04');
INSERT INTO `neuedu_order` VALUES ('31', '23', '1544404043890', '2', '50.00', '1', '0', '10', '2018-12-10 09:07:58', null, null, null, '2018-12-10 09:07:23', '2018-12-10 09:07:56');

-- ----------------------------
-- Table structure for neuedu_order_item
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_order_item`;
CREATE TABLE `neuedu_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(11) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位元，保留两位小数',
  `quantity` int(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '商品总价，单位元，保留两位小数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_order_item
-- ----------------------------
INSERT INTO `neuedu_order_item` VALUES ('25', '29', '1543647331297', '10000', '苹果', '1', '1.00', '1', '1.00', '2018-12-01 14:55:31', '2018-12-01 14:55:31');
INSERT INTO `neuedu_order_item` VALUES ('26', '29', '1543647331297', '10001', '啊啊是', '1', '1.00', '1', '1.00', '2018-12-01 14:55:31', '2018-12-01 14:55:31');
INSERT INTO `neuedu_order_item` VALUES ('27', '29', '1543647331297', '10002', '订单是', '2', '2.00', '1', '2.00', '2018-12-01 14:55:31', '2018-12-01 14:55:31');
INSERT INTO `neuedu_order_item` VALUES ('28', '29', '1544058244925', '10000', '苹果', '1', '1.00', '1', '1.00', '2018-12-06 09:04:05', '2018-12-06 09:04:05');
INSERT INTO `neuedu_order_item` VALUES ('29', '29', '1544058244925', '10001', '啊啊是', '1', '1.00', '1', '1.00', '2018-12-06 09:04:05', '2018-12-06 09:04:05');
INSERT INTO `neuedu_order_item` VALUES ('30', '29', '1544058688327', '10000', '苹果', '1', '1.00', '1', '1.00', '2018-12-06 09:11:28', '2018-12-06 09:11:28');
INSERT INTO `neuedu_order_item` VALUES ('31', '29', '1544058688327', '10001', '啊啊是', '1', '1.00', '1', '1.00', '2018-12-06 09:11:28', '2018-12-06 09:11:28');
INSERT INTO `neuedu_order_item` VALUES ('32', '29', '1544084214204', '10005', '版本', '5', '5.00', '1', '5.00', '2018-12-06 16:16:54', '2018-12-06 16:16:54');
INSERT INTO `neuedu_order_item` VALUES ('33', '30', '1544088704723', '10001', '啊啊是', '1', '1.00', '10', '10.00', '2018-12-06 17:31:44', '2018-12-06 17:31:44');
INSERT INTO `neuedu_order_item` VALUES ('34', '30', '1544088704723', '10002', '订单是', '2', '2.00', '10', '20.00', '2018-12-06 17:31:44', '2018-12-06 17:31:44');
INSERT INTO `neuedu_order_item` VALUES ('35', '29', '1544106756242', '10004', '方法是', '4', '4.00', '10', '40.00', '2018-12-06 22:32:36', '2018-12-06 22:32:36');
INSERT INTO `neuedu_order_item` VALUES ('36', '29', '1544106756242', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-06 22:32:36', '2018-12-06 22:32:36');
INSERT INTO `neuedu_order_item` VALUES ('37', '29', '1544108518356', '10004', '方法是', '4', '4.00', '10', '40.00', '2018-12-06 23:01:58', '2018-12-06 23:01:58');
INSERT INTO `neuedu_order_item` VALUES ('38', '29', '1544108518356', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-06 23:01:58', '2018-12-06 23:01:58');
INSERT INTO `neuedu_order_item` VALUES ('39', '29', '1544108730074', '10004', '方法是', '4', '4.00', '10', '40.00', '2018-12-06 23:05:30', '2018-12-06 23:05:30');
INSERT INTO `neuedu_order_item` VALUES ('40', '29', '1544108730074', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-06 23:05:30', '2018-12-06 23:05:30');
INSERT INTO `neuedu_order_item` VALUES ('41', '29', '1544109601845', '10004', '方法是', '4', '4.00', '10', '40.00', '2018-12-06 23:20:01', '2018-12-06 23:20:01');
INSERT INTO `neuedu_order_item` VALUES ('42', '29', '1544109601845', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-06 23:20:01', '2018-12-06 23:20:01');
INSERT INTO `neuedu_order_item` VALUES ('43', '29', '1544109803770', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-06 23:23:23', '2018-12-06 23:23:23');
INSERT INTO `neuedu_order_item` VALUES ('44', '29', '1544110018468', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-06 23:26:58', '2018-12-06 23:26:58');
INSERT INTO `neuedu_order_item` VALUES ('45', '29', '1544111407014', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-06 23:50:07', '2018-12-06 23:50:07');
INSERT INTO `neuedu_order_item` VALUES ('46', '29', '1544114781328', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-07 00:46:21', '2018-12-07 00:46:21');
INSERT INTO `neuedu_order_item` VALUES ('47', '29', '1544115250443', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-07 00:54:10', '2018-12-07 00:54:10');
INSERT INTO `neuedu_order_item` VALUES ('48', '29', '1544365237813', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-09 22:20:37', '2018-12-09 22:20:37');
INSERT INTO `neuedu_order_item` VALUES ('49', '29', '1544366675071', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-09 22:44:35', '2018-12-09 22:44:35');
INSERT INTO `neuedu_order_item` VALUES ('50', '29', '1544367815453', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-09 23:03:35', '2018-12-09 23:03:35');
INSERT INTO `neuedu_order_item` VALUES ('51', '29', '1544368367955', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-09 23:12:48', '2018-12-09 23:12:48');
INSERT INTO `neuedu_order_item` VALUES ('52', '23', '1544404043890', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-10 09:01:28', '2018-12-10 09:01:28');
INSERT INTO `neuedu_order_item` VALUES ('53', '23', '1544404043890', '10005', '版本', '5', '5.00', '10', '50.00', '2018-12-10 09:07:23', '2018-12-10 09:07:23');

-- ----------------------------
-- Table structure for neuedu_payinfo
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_payinfo`;
CREATE TABLE `neuedu_payinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台 1-支付宝 2-微信',
  `platform_number` varchar(200) DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_payinfo
-- ----------------------------
INSERT INTO `neuedu_payinfo` VALUES ('1', '29', '1544115250443', '1', '2018120722001416960500604128', 'TRADE_SUCCESS', '2018-12-07 00:55:01', '2018-12-07 00:55:01');
INSERT INTO `neuedu_payinfo` VALUES ('2', '29', '1544403688559', '1', '2018121022001416960500618407', 'TRADE_SUCCESS', '2018-12-10 09:05:04', '2018-12-10 09:05:04');
INSERT INTO `neuedu_payinfo` VALUES ('3', '29', '1544404043890', '1', '2018121022001416960500618408', 'TRADE_SUCCESS', '2018-12-10 09:07:56', '2018-12-10 09:07:56');

-- ----------------------------
-- Table structure for neuedu_product
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_product`;
CREATE TABLE `neuedu_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '类别id，对应neuedu_category表的主键',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图，url相对地址',
  `sub_images` text COMMENT '图片地址，json格式',
  `detail` text COMMENT '商品详情',
  `price` decimal(20,2) NOT NULL COMMENT '价格，单位-元保留两位小数',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `status` int(6) DEFAULT '1' COMMENT '商品状态，1-在售 2-下架 3-删除',
  `is_new` tinyint(1) DEFAULT '0' COMMENT '是否新品',
  `is_hot` tinyint(1) DEFAULT '0' COMMENT '是否热门',
  `is_banner` tinyint(1) DEFAULT '0' COMMENT '是否轮播',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10007 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_product
-- ----------------------------
INSERT INTO `neuedu_product` VALUES ('10000', '1', '苹果', '1', '1', '1', '1', '1.00', '100', '1', '1', '0', '0', '2018-11-27 19:26:55', '2018-11-28 09:36:41');
INSERT INTO `neuedu_product` VALUES ('10001', '1', '啊啊是', '1', '1', '1', '1', '1.00', '90', '1', '1', '0', '0', '2018-11-28 00:49:20', '2018-11-28 00:49:23');
INSERT INTO `neuedu_product` VALUES ('10002', '2', '订单是', '2', '2', '2', '2', '2.00', '90', '1', '0', '1', '0', '2018-11-28 00:49:47', '2018-11-28 00:49:52');
INSERT INTO `neuedu_product` VALUES ('10003', '3', '搜索', '3', '3', '3', '3', '3.00', '100', '1', '0', '1', '0', '2018-11-28 00:50:13', '2018-11-28 00:50:15');
INSERT INTO `neuedu_product` VALUES ('10004', '4', '方法是', '4', '4', '4', '4', '4.00', '60', '1', '0', '0', '1', '2018-11-28 00:50:27', '2018-11-28 00:50:31');
INSERT INTO `neuedu_product` VALUES ('10005', '5', '版本', '5', '5', '5', '5', '5.00', '50', '1', '0', '0', '1', '2018-11-28 00:50:47', '2018-11-28 00:50:50');
INSERT INTO `neuedu_product` VALUES ('10006', '6', '好是', '6', '6', '6', '6', '6.00', '100', '1', '0', '0', '0', '2018-11-28 00:51:04', '2018-11-28 00:51:07');

-- ----------------------------
-- Table structure for neuedu_product1
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_product1`;
CREATE TABLE `neuedu_product1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '类别id，对应neuedu_category表的主键',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图，url相对地址',
  `sub_images` text COMMENT '图片地址，json格式',
  `detail` text COMMENT '商品详情',
  `price` decimal(20,2) NOT NULL COMMENT '价格，单位-元保留两位小数',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `status` int(6) DEFAULT '1' COMMENT '商品状态，1-在售 2-下架 3-删除',
  `is_new` tinyint(1) DEFAULT '0' COMMENT '是否新品',
  `is_hot` tinyint(1) DEFAULT '0' COMMENT '是否热门',
  `is_banner` tinyint(1) DEFAULT '0' COMMENT '是否轮播',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_product1
-- ----------------------------

-- ----------------------------
-- Table structure for neuedu_shipping
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_shipping`;
CREATE TABLE `neuedu_shipping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_shipping
-- ----------------------------
INSERT INTO `neuedu_shipping` VALUES ('2', '29', 'zhangsan', '010', '18688888888', '天津', '北京市', null, '中关村', '100000', '2018-12-01 13:01:09', '2018-12-01 13:01:09');
INSERT INTO `neuedu_shipping` VALUES ('3', '29', 'zhangsan', '010', '18688888888', '北京', '北京市', null, '中关村', '100000', '2018-12-01 13:08:16', '2018-12-01 13:08:16');
INSERT INTO `neuedu_shipping` VALUES ('4', '29', 'zhangsan', '010', '18688888888', '北京', '北京市', null, '中关村', '100000', '2018-12-01 13:08:18', '2018-12-01 13:08:18');
INSERT INTO `neuedu_shipping` VALUES ('5', '29', 'zhangsan', '010', '18688888888', '北京', '北京市', null, '中关村', '100000', '2018-12-01 13:10:59', '2018-12-01 13:10:59');

-- ----------------------------
-- Table structure for neuedu_user
-- ----------------------------
DROP TABLE IF EXISTS `neuedu_user`;
CREATE TABLE `neuedu_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码,MD5加密',
  `email` varchar(50) DEFAULT NULL COMMENT '用户email',
  `phone` varchar(20) DEFAULT NULL COMMENT '用户phone',
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_user
-- ----------------------------
INSERT INTO `neuedu_user` VALUES ('21', 'ceshi', '123', '1234ss', '123', '123', '123', '123', '2018-11-26 11:20:32', '2018-11-26 11:20:38');
INSERT INTO `neuedu_user` VALUES ('22', 'zhangxin', '0b4e7a0e5fe84ad35fb5f95b9ceeac79', '123@123', '18202296570', '我的公司', '东软', '1', '2018-11-26 16:39:51', '2018-11-26 23:00:24');
INSERT INTO `neuedu_user` VALUES ('23', 'zhangxiao', '123456', '123456@123456', '18202296570', '我的公司', '东软', '1', '2018-11-26 16:43:15', '2018-11-26 16:43:15');
INSERT INTO `neuedu_user` VALUES ('26', 'zhangxin3', '123', '123@1324', '18202296570', '', '写代码', '1', '2018-11-26 16:55:54', '2018-11-26 16:55:54');
INSERT INTO `neuedu_user` VALUES ('27', 'MD5ceshi', '0b4e7a0e5fe84ad35fb5f95b9ceeac79', '123@1231', '111', '111', '111', '1', '2018-11-26 17:05:28', '2018-11-27 00:55:21');
INSERT INTO `neuedu_user` VALUES ('28', 'zhangxin4', '96e79218965eb72c92a549dd5a330112', '317237281@qq.com', '222', '222', '222', '1', '2018-11-27 01:00:41', '2018-11-27 09:32:42');
INSERT INTO `neuedu_user` VALUES ('29', 'chaoji', '202cb962ac59075b964b07152d234b70', 'qwer@123', '111', '111', '222', '1', '2018-11-28 17:03:56', '2018-11-28 17:03:56');
INSERT INTO `neuedu_user` VALUES ('30', 'zx2', '202cb962ac59075b964b07152d234b70', 'qwer@12311144', '111', '111', '222', '1', '2018-12-06 17:29:46', '2018-12-06 17:29:46');
INSERT INTO `neuedu_user` VALUES ('31', 'xiaoxiaoxiao', '123', null, null, null, null, '1', '2019-03-25 15:41:17', '2019-03-25 15:41:17');
INSERT INTO `neuedu_user` VALUES ('32', 'admin', '123', null, null, null, null, '1', '2019-04-03 11:01:11', '2019-04-03 11:01:14');

-- ----------------------------
-- Table structure for prod
-- ----------------------------
DROP TABLE IF EXISTS `prod`;
CREATE TABLE `prod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(255) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `pimage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of prod
-- ----------------------------
INSERT INTO `prod` VALUES ('1', '苹果', '10', 'imgs/download.jpg');
INSERT INTO `prod` VALUES ('2', '苹果', '10', 'imgs/download.jpg');
INSERT INTO `prod` VALUES ('3', '苹果', '10', 'imgs/download.jpg');
INSERT INTO `prod` VALUES ('4', '苹果', '10', 'imgs/download.jpg');
INSERT INTO `prod` VALUES ('5', '苹果', '10', 'imgs/download.jpg');
INSERT INTO `prod` VALUES ('6', '苹果', '10', 'imgs/download.jpg');

-- ----------------------------
-- Table structure for ssss
-- ----------------------------
DROP TABLE IF EXISTS `ssss`;
CREATE TABLE `ssss` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uanme` varchar(255) NOT NULL,
  `psd` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unam` (`uanme`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ssss
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ages` tinyint(4) NOT NULL,
  `uname` varchar(20) DEFAULT NULL,
  `psd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '10', 'zhangxin1', '123');
INSERT INTO `users` VALUES ('2', '10', 'zhangxin2', '123');
INSERT INTO `users` VALUES ('3', '10', 'zhangxin3', '123');
INSERT INTO `users` VALUES ('4', '10', 'zhangxin4', '123');

-- ----------------------------
-- Table structure for uu
-- ----------------------------
DROP TABLE IF EXISTS `uu`;
CREATE TABLE `uu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) DEFAULT NULL,
  `upsd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uu
-- ----------------------------
INSERT INTO `uu` VALUES ('1', 'zhangxin', '123');
INSERT INTO `uu` VALUES ('2', 'zhangxin', '123');
INSERT INTO `uu` VALUES ('3', 'zhangxin', '123');
INSERT INTO `uu` VALUES ('4', 'zhangxin', '123');
INSERT INTO `uu` VALUES ('5', 'zhangxin', '123');
INSERT INTO `uu` VALUES ('6', 'zhangxin', '123');
INSERT INTO `uu` VALUES ('7', 'zhangxin', '123');
INSERT INTO `uu` VALUES ('9', '最帅的人', '123');
INSERT INTO `uu` VALUES ('10', '最帅的人', '123');
INSERT INTO `uu` VALUES ('11', '最帅的人', '123');
INSERT INTO `uu` VALUES ('12', '最帅的人', '123');
INSERT INTO `uu` VALUES ('13', '最帅的人', '1');
INSERT INTO `uu` VALUES ('14', '最帅的人', '1');
INSERT INTO `uu` VALUES ('15', '最帅的人', '1');
INSERT INTO `uu` VALUES ('16', '最帅的人', '1');
INSERT INTO `uu` VALUES ('17', '最帅的人', '1');
INSERT INTO `uu` VALUES ('18', '最帅的人', '1');
INSERT INTO `uu` VALUES ('19', '最帅的人', '1');
INSERT INTO `uu` VALUES ('20', '最帅的人', '1');
INSERT INTO `uu` VALUES ('21', '最帅的人', '1');
INSERT INTO `uu` VALUES ('22', '最帅的人', '1');
INSERT INTO `uu` VALUES ('23', '最帅的人', '1');
INSERT INTO `uu` VALUES ('24', '最帅的人', '1');
INSERT INTO `uu` VALUES ('25', '最帅的人', '11');
INSERT INTO `uu` VALUES ('26', '最帅的人', '2');
INSERT INTO `uu` VALUES ('27', '最帅的人', '2');
INSERT INTO `uu` VALUES ('28', '最帅的人', '1');
INSERT INTO `uu` VALUES ('29', '最帅的人', '1');
INSERT INTO `uu` VALUES ('30', '最帅的人', '1');
INSERT INTO `uu` VALUES ('31', '最帅的人', '1');
INSERT INTO `uu` VALUES ('32', '最帅的人', '1');
INSERT INTO `uu` VALUES ('33', '最帅的人', '1');
INSERT INTO `uu` VALUES ('34', '最帅的人', '1');
INSERT INTO `uu` VALUES ('35', '最帅的人', '2');
INSERT INTO `uu` VALUES ('36', '最帅的人111', '2');
INSERT INTO `uu` VALUES ('37', 'zhangxin1', '123');
INSERT INTO `uu` VALUES ('38', 'å°å°', '123');
INSERT INTO `uu` VALUES ('39', 'å°å°', '123');
INSERT INTO `uu` VALUES ('40', 'xiaoxiao', '1');
