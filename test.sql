/*
SQLyog Ultimate v9.62 
MySQL - 5.0.96-community-log : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;


USE `test`;

/*Table structure for table `demo` */

DROP TABLE IF EXISTS `demo`;

CREATE TABLE `demo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) DEFAULT NULL,
  `password` VARCHAR(40) DEFAULT NULL,
  `createTime` DATETIME DEFAULT NULL,
  `type` TINYINT(4) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=MYISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `demo` */

insert  into `demo`(`id`,`name`,`password`,`createTime`,`type`) values (2,'newname','000000','2014-07-19 22:04:26',1),(3,'newname','000000','2014-07-18 22:04:34',2),(4,'newname','000000','2014-07-17 22:04:40',2),(5,'newname','000000','2014-07-16 22:04:46',3),(19,'newname','000000','2014-11-16 00:11:23',4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
