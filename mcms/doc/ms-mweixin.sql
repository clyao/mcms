/*
 Navicat Premium Data Transfer

 Source Server         : www.ming-soft.com
 Source Server Type    : MySQL
 Source Server Version : 50131
 Source Host           : 115.239.227.186
 Source Database       : db-ms

 Target Server Type    : MySQL
 Target Server Version : 50131
 File Encoding         : utf-8

 Date: 12/31/2015 15:00:26 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `wx_menu`
-- ----------------------------
DROP TABLE IF EXISTS `wx_menu`;
CREATE TABLE `wx_menu` (
  `MENU_ID` int(22) NOT NULL AUTO_INCREMENT COMMENT '菜单自增长编号',
  `MENU_APP_ID` int(22) DEFAULT NULL COMMENT '菜单所属商家编号',
  `MENU_TITLE` varchar(15) DEFAULT NULL COMMENT '单菜名称',
  `MENU_URL` text COMMENT '单菜链接地址',
  `MENU_STATUS` int(1) DEFAULT NULL COMMENT '菜单状态 0：不启用 1：启用',
  `MENU_MENU_ID` int(22) DEFAULT NULL COMMENT '父菜单编号',
  `MENU_TYPE` int(2) DEFAULT NULL COMMENT '菜单属性 0:链接 1:回复',
  `MENU_SORT` int(11) DEFAULT NULL,
  `MENU_STYLE` int(11) DEFAULT NULL,
  `MENU_OAUTH_ID` int(11) NOT NULL COMMENT '授权数据编号',
  `MENU_WEIXIN_ID` int(11) DEFAULT NULL COMMENT '微信编号',
  PRIMARY KEY (`MENU_ID`,`MENU_OAUTH_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='微信菜单';

-- ----------------------------
--  Table structure for `wx_news`
-- ----------------------------
DROP TABLE IF EXISTS `wx_news`;
CREATE TABLE `wx_news` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT,
  `news_type` int(11) DEFAULT '2' COMMENT '素材类型　1.图文 2.文本 3.图片',
  `news_master_article_id` int(11) DEFAULT '0' COMMENT '图文素材时有效,主图文id',
  `news_child_article_ids` varchar(255) DEFAULT NULL COMMENT '图文素材有效',
  `news_datetime` datetime DEFAULT NULL,
  `news_App_Id` int(11) DEFAULT '0',
  `news_content` text,
  `news_category_ID` int(11) DEFAULT NULL,
  `news_weixin_ID` int(11) DEFAULT NULL COMMENT '微信编号',
  PRIMARY KEY (`news_id`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `wx_passive_message`
-- ----------------------------
DROP TABLE IF EXISTS `wx_passive_message`;
CREATE TABLE `wx_passive_message` (
  `PM_WEIXIN_ID` int(11) NOT NULL COMMENT '微信编号',
  `PM_ID` int(22) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `PM_EVENT_ID` int(22) NOT NULL COMMENT '该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框',
  `PM_NEWS_ID` int(22) DEFAULT '0' COMMENT '回复的素材ID',
  `PM_MESSAGE_ID` int(11) NOT NULL COMMENT '对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个',
  `PM_APP_ID` int(22) NOT NULL COMMENT '该回复所属的应用ID',
  `PM_REPLY_NUM` int(22) DEFAULT '0' COMMENT '被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复',
  `PM_KEY` varchar(300) DEFAULT NULL COMMENT '事件关键字',
  `PM_TYPE` int(2) NOT NULL COMMENT '回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)',
  `PM_TAG` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`PM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='微信被动消息回复';

-- ----------------------------
--  Table structure for `wx_passive_message_log`
-- ----------------------------
DROP TABLE IF EXISTS `wx_passive_message_log`;
CREATE TABLE `wx_passive_message_log` (
  `PML_WEIXIN_ID` int(11) NOT NULL COMMENT '微信编号',
  `PML_ID` int(22) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `PML_APP_ID` int(22) NOT NULL COMMENT '关键的应用ID',
  `PML_PEOPLE_ID` int(22) NOT NULL COMMENT '关联用户ID',
  `PML_PASSIVE_MESSAGE_ID` int(22) NOT NULL COMMENT '关联被动回复消息ID',
  `PML_EVENT_ID` int(22) NOT NULL COMMENT '关联事件分类ID',
  `PML_TIME` datetime NOT NULL COMMENT '日志生成时间',
  `PML_KEY` varchar(300) DEFAULT NULL COMMENT '关键字',
  PRIMARY KEY (`PML_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1201 DEFAULT CHARSET=utf8 COMMENT='被动回复消息日志记录';

-- ----------------------------
--  Table structure for `wx_people`
-- ----------------------------
DROP TABLE IF EXISTS `wx_people`;
CREATE TABLE `wx_people` (
  `PW_WEIXIN_ID` int(11) NOT NULL,
  `PW_PEOPLE_ID` int(22) NOT NULL DEFAULT '0' COMMENT '关联用户基本信息ID',
  `PW_APP_ID` int(22) DEFAULT NULL COMMENT '用户所关注微信号的ID',
  `PW_OPEN_ID` varchar(100) DEFAULT NULL COMMENT '户用在微信中的唯一标识',
  `PW_PEOPLE_STATE` int(2) DEFAULT NULL,
  `PW_PROVINCE` varchar(50) DEFAULT NULL COMMENT '用户所在省份',
  `PW_CITY` varchar(50) DEFAULT NULL COMMENT '户用所在城市',
  `PW_HEADIMG_URL` varchar(500) DEFAULT NULL COMMENT '户用头像链接地址',
  PRIMARY KEY (`PW_PEOPLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `wx_weixin`
-- ----------------------------
DROP TABLE IF EXISTS `wx_weixin`;
CREATE TABLE `wx_weixin` (
  `WEIXIN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `APP_ID` int(22) NOT NULL COMMENT '微信公众号所属用户编号',
  `WEIXIN_NO` varchar(30) DEFAULT NULL COMMENT '信号号',
  `WEIXIN_ORIGIN_ID` varchar(30) DEFAULT NULL COMMENT '微信原始ID',
  `WEIXIN_NAME` varchar(30) NOT NULL COMMENT '众号公名称',
  `WEIXIN_TYPE` int(1) NOT NULL DEFAULT '0' COMMENT '信微号类型 0：服务号 1：订阅号',
  `WEIXIN_TOKEN` varchar(30) DEFAULT NULL COMMENT '信微token',
  `WEIXIN_IMAGE` varchar(100) DEFAULT NULL COMMENT '信微二维码图片',
  `WEIXIN_APPID` varchar(150) NOT NULL COMMENT '用应编号',
  `WEIXIN_APPSECRET` varchar(150) NOT NULL COMMENT '用应授权码',
  `WEIXIN_HEADIMG` varchar(150) DEFAULT NULL COMMENT '信微帐号头像',
  `WEIXIN_MCHID` varchar(255) DEFAULT NULL COMMENT '微信支付商户号',
  `WEIXIN_PAYKEY` varchar(255) DEFAULT NULL COMMENT '支付key',
  `WEIXIN_RROXY_URL` varchar(500) DEFAULT NULL COMMENT '微信内网测试地址',
  `WEIXIN_OAUTH_URL` varchar(200) DEFAULT NULL COMMENT '网页2.0授权地址,需要加http',
  PRIMARY KEY (`WEIXIN_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT=' 微信公众帐号';

SET FOREIGN_KEY_CHECKS = 1;
