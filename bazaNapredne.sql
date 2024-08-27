/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 10.4.21-MariaDB : Database - 1napredne
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`1napredne` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `1napredne`;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `adminID` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`adminID`),
  KEY `fk_useer` (`userID`),
  CONSTRAINT `fk_useer` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `administrator` */

insert  into `administrator`(`adminID`,`firstName`,`lastName`,`email`,`userID`) values 
(1,'Marko','Markovic','mare@admin.fon.bg.ac.rs',6),
(2,'Dara','Daric','dara@admin.fon.bg.ac.rs',5),
(3,'Milica','Papic','mica@admin.fon.bg.ac.rs',3),
(4,'Tara','Paunovic','tara@admin.fon.bg.ac.rs',2),
(5,'Milan','Ivic','milan@admin.fon.bg.ac.rs',4);

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `employeeID` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `dateOfBirth` datetime NOT NULL,
  `contactNumber` varchar(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `teamID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`employeeID`),
  KEY `fk_team` (`teamID`),
  KEY `fk_user` (`userID`),
  CONSTRAINT `fk_team` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`),
  CONSTRAINT `fk_user` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `employee` */

insert  into `employee`(`employeeID`,`firstName`,`lastName`,`email`,`dateOfBirth`,`contactNumber`,`address`,`teamID`,`userID`) values 
(1,'Valentina','Vidojevic','val@gmail.com','2000-07-15 00:00:00','0605241256','Vojvode Stepe 20, Beograd',2,7),
(2,'Milan','Ivic','mil@gmail.com','2000-01-01 00:00:00','0658476912','Tosin bunar 153, Beograd',3,8),
(3,'Mara','Maric','mara@gmail.com','2000-02-20 00:00:00','0605201450','Kneza Milosa 15, Beograd',3,1);

/*Table structure for table `employmentprojectassignment` */

DROP TABLE IF EXISTS `employmentprojectassignment`;

CREATE TABLE `employmentprojectassignment` (
  `assignmentID` int(11) NOT NULL AUTO_INCREMENT,
  `projectID` int(11) NOT NULL,
  `employeeID` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`assignmentID`,`projectID`,`employeeID`),
  KEY `fk_proj` (`projectID`),
  KEY `fk_eplo` (`employeeID`),
  CONSTRAINT `fk_eplo` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`),
  CONSTRAINT `fk_proj` FOREIGN KEY (`projectID`) REFERENCES `project` (`projectID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `employmentprojectassignment` */

insert  into `employmentprojectassignment`(`assignmentID`,`projectID`,`employeeID`,`description`,`status`) values 
(1,1,1,'Izrada Home strane','RADI'),
(2,2,2,'Izrada klijentske strane','RADI'),
(3,2,3,'Izrada servera','RADI');

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `projectID` int(11) NOT NULL AUTO_INCREMENT,
  `projectName` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime NOT NULL,
  `teamID` int(11) DEFAULT NULL,
  PRIMARY KEY (`projectID`),
  KEY `fk_t` (`teamID`),
  CONSTRAINT `fk_t` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `project` */

insert  into `project`(`projectID`,`projectName`,`description`,`startDate`,`endDate`,`teamID`) values 
(1,'Web1','Pravljenje sajta','2023-09-12 15:19:50','2023-10-12 15:20:03',1),
(2,'GoTo','Izrada desktop aplikacije','2023-09-21 15:21:01','2023-10-18 15:21:06',2),
(3,'Bussiness','Izrada biznis aplikacije','2023-09-20 18:35:51','2023-10-18 18:35:55',3);

/*Table structure for table `salary` */

DROP TABLE IF EXISTS `salary`;

CREATE TABLE `salary` (
  `salaryID` int(11) NOT NULL AUTO_INCREMENT,
  `employeeID` int(11) NOT NULL,
  `salaryAmount` double NOT NULL,
  `paymentDate` date NOT NULL,
  PRIMARY KEY (`salaryID`,`employeeID`),
  KEY `fk_empl` (`employeeID`),
  CONSTRAINT `fk_empl` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `salary` */

insert  into `salary`(`salaryID`,`employeeID`,`salaryAmount`,`paymentDate`) values 
(1,1,90000,'2023-09-11'),
(2,2,120000,'2023-09-05'),
(3,3,50000,'2023-09-20');

/*Table structure for table `team` */

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `teamID` int(11) NOT NULL AUTO_INCREMENT,
  `teamName` varchar(255) NOT NULL,
  `maxNumber` int(11) NOT NULL,
  PRIMARY KEY (`teamID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `team` */

insert  into `team`(`teamID`,`teamName`,`maxNumber`) values 
(1,'Tim 1',10),
(2,'Tim 2',15),
(3,'Tim 3',0);

/*Table structure for table `timeoffrequest` */

DROP TABLE IF EXISTS `timeoffrequest`;

CREATE TABLE `timeoffrequest` (
  `requestID` int(11) NOT NULL AUTO_INCREMENT,
  `startDate` datetime NOT NULL,
  `endDate` datetime NOT NULL,
  `reason` varchar(255) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `employeeID` int(11) DEFAULT NULL,
  PRIMARY KEY (`requestID`),
  KEY `fk_emp` (`employeeID`),
  CONSTRAINT `fk_emp` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `timeoffrequest` */

insert  into `timeoffrequest`(`requestID`,`startDate`,`endDate`,`reason`,`status`,`employeeID`) values 
(1,'2023-09-14 18:37:28','2023-09-30 18:37:22','Zdravstveni razlog','U OBRADI',1),
(2,'2023-09-18 18:37:37','2023-09-21 18:37:41','Porodicna situacija','PRIHVACEN',3);

/*Table structure for table `token` */

DROP TABLE IF EXISTS `token`;

CREATE TABLE `token` (
  `tokenID` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(1000) NOT NULL,
  `tokenType` varchar(255) NOT NULL,
  `expired` tinyint(4) NOT NULL,
  `userID` int(11) NOT NULL,
  PRIMARY KEY (`tokenID`,`userID`),
  KEY `fk_userr` (`userID`),
  CONSTRAINT `fk_userr` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `token` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`userID`,`username`,`password`,`role`) values 
(1,'mara@gmail.com','mara1','USER'),
(2,'tara@admin.fon.bg.ac.rs','tara1','ADMIN'),
(3,'mica@admin.fon.bg.ac.rs','mica1','ADMIN'),
(4,'milan@admin.fon.bg.ac.rs','milan1','ADMIN'),
(5,'dara@admin.fon.bg.ac.rs','dara1','ADMIN'),
(6,'mare@admin.fon.bg.ac.rs','mare1','ADMIN'),
(7,'val@gmail.com','val1','USER'),
(8,'mil@gmail.com','mil1','USER');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
