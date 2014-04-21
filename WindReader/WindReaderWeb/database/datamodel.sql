SET NAMES 'utf8';
DROP DATABASE IF EXISTS `reader`;
CREATE DATABASE `reader` DEFAULT CHARSET=utf8;;
USE `reader`;

DROP TABLE IF EXISTS `rss_provider`;
CREATE TABLE `rss_provider` (
	`id` bigint(20) AUTO_INCREMENT,
	`name` varchar(100) NOT NULL,
	`url` varchar(500),
	`description` varchar(500),
	`category` bigint(20),
	`parent_id` bigint(20), 
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `rss_content`;
CREATE TABLE `rss_content` (
	`id` bigint(20) AUTO_INCREMENT,
	`title` varchar(500) NOT NULL,
 	`link` varchar(500),
 	`description` varchar(10000),
 	`pub_date` bigint(20),
 	`guid` varchar(500),
 	`source` varchar(100),
 	`category_id` bigint(20),
 	`category` varchar(100),
 	`comments` varchar(500),
 	`provider_id` bigint(20),
 	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`(
	`id` bigint(20) AUTO_INCREMENT,
	`title` varchar(200) NOT NULL,
	`description` varchar(500),
	`parent_id` bigint(20) NOT NULL,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `rss_content` ADD COLUMN hash varchar(64);