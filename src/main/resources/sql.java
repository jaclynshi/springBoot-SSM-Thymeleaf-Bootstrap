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
  `permission` int(2) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		
-- ----------------------------
-- Table structure for followUser
-- ----------------------------
DROP TABLE IF EXISTS `followUser`;
CREATE TABLE `followUser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `followFrom` int(11) NOT NULL,
  `followTo` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		
-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `weboID` int(11) NOT NULL,
  `commentFrom` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `commentTime` TIMESTAMP NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;