SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `name` varchar(255) NULL,
  `password` varchar(255) NOT NULL,
  `image` varchar(255) NULL,
  `agent` smallint(2) NULL,
  `phone` varchar(255) NULL,
  `address` varchar(255) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
		
-- ----------------------------
-- Table structure for webo
-- ----------------------------
DROP TABLE IF EXISTS `webo`;
CREATE TABLE `webo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `image` varchar(255) NULL,
  `commentCount` int(11) NOT NULL DEFAULT 0,
  `publishTime` TIMESTAMP NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;