-- MariaDB dump 10.17  Distrib 10.4.13-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: EPS_SYSTEM
-- ------------------------------------------------------
-- Server version	10.4.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES ('coordinador1','1111111111111','111111111',NULL,'Oliver ','Sierra','coordinador1@coordinador1.com','11111111','','PBKDF2WithHmacSHA256:3072:bh/wgbRYDYJUbpRvu9PZm8uef4M3sYvvC5vYUGIfUBHjQipqblGxQeQdDzaa9rBOf4aP5C+x6bHsv1HTDIWspA==:mgHZ/c2wizFg36geswbicXP9M8/m0+7PX2XOQtV6f3o=',4,'',1,0),('coordinador2','2222222222222','222222222',NULL,'Francisco','Rojas','coordinador2@coordinador2.com','22222222','','PBKDF2WithHmacSHA256:3072:S/K2YgT16M+A5TJNaKw1+z1KrI8tedHbQJcvK/VhcmHBFVdMdikdxYXUh48pe2X+SGlBtzBBj1iqWY7UAPyXjw==:/gIlSlgxuQqE/IpqaOiK67KYzjyPb7fKUprdxfUjfFA=',4,'',1,0),('coordinadorEPS','5555555555555','555555555',NULL,'Efrain','Aballi','coordinadorEPS@coordinadorEPS.com','55555555','','PBKDF2WithHmacSHA256:3072:pRHO5dwN79U/ehewfFY019evyHR9IOw9xa+elhLWDTgEY0uGXT5IiEVbBqZV3Yh0K3oIB1GVolYd5hBOOoi4qA==:2syeCL23CnP8cyus1pYFZRRCXySYlc9uiIgPc07RcjU=',2,'',1,1),('estudiante1','3333333333333',NULL,'333333333','Crystian Rafael','López Santay','crystianrafael-lopezsantay@cunoc.edu.gt','33333333','','PBKDF2WithHmacSHA256:3072:fkNKvqrR5UF9F+ZrPBmM/iHqXhfKUgh5b5NvSo7wZtN1gTE97QN+YIsxAiIyMQWMnLmuO4b8RPontRZs2tY8KA==:fNDhXigzwaIVEymNiNTtmHx1bRiStzOsYvfk5Qxy27U=',6,'',1,0),('estudiante2','4444444444444',NULL,'444444444','Edvin Teodoro','Gonzales Rafael','estudiante2@estudiante2.com','44444444','','PBKDF2WithHmacSHA256:3072:Og+6Vc/N8PZZDrnaxrNEL+5Jf2iA+nQH07c39l8WxUfsmgyCkl9rHS/J7M1MWu6DJn11CoTFqd0wMHTenLuFcA==:UR5jEHKXeQ7mO06uA6koYyhRDdekbHBJTcvForZkCRI=',6,'',1,0),('secretariaEPS','201330873','201330873',NULL,'secretaria','secretaria','secretaria@gmail.com','201330873',NULL,'PBKDF2WithHmacSHA256:3072:uOZm6rISD7pg3Z1scq1myP+ueWArrf1/v4GZEAjqnwV2WcPNSZMKnRZuoX/jJFx6R4YllZmEYz0n2xnb1VI6gw==:w3nbuw0TDn+qqEayLGfWMFK4rAqkmV7hYBARtRbbgvw=',3,NULL,1,0),('supervisorEPS1','6666666666666','666666666',NULL,'supervisorEPS1','supervisorEPS1','supervisorEPS1@supervisorEPS1.com','66666666','','PBKDF2WithHmacSHA256:3072:/gG0BuPV4ylXypVbY2CT4ZeNRCoqGCnM5hselmELnuohKAQ5RGbKwAultylCY/8+9yOeXKfoeqvuIHikrwT3WA==:0vJ2rnkLd4VdqsVW2WW3/alVLgUcXZwJbMYp+ok1Rf8=',9,'',1,1),('supervisorEPS2','7777777777777','777777777',NULL,'supervisorEPS2','supervisorEPS2','supervisorEPS2@supervisorEPS2.com','77777777','','PBKDF2WithHmacSHA256:3072:ngQtTpHnjUrNuSwJAGbFnIe14IrNCshxCX9NicbWy5UtyKwAgcYkhWPGlUvuxQ5qd+OGvt+4yfJOrQAuf7KzXQ==:cFc6KKUtVauonLJo2hQbgDDmPoxvDwkyhVuKPFYDTPE=',9,'',1,0);
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `USER_CAREER`
--

LOCK TABLES `USER_CAREER` WRITE;
/*!40000 ALTER TABLE `USER_CAREER` DISABLE KEYS */;
INSERT INTO `USER_CAREER` VALUES (1,'coordinador1',58,NULL),(2,'coordinador2',58,NULL),(3,'estudiante1',58,NULL),(4,'estudiante2',58,NULL),(5,'supervisorEPS1',58,NULL),(6,'supervisorEPS2',58,NULL);
/*!40000 ALTER TABLE `USER_CAREER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-01 19:47:55
