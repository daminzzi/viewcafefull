-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: stg-yswa-kr-practice-db-master.mariadb.database.azure.com    Database: s10p12a601
-- ------------------------------------------------------
-- Server version	5.6.47.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `conference_reservation`
--

DROP TABLE IF EXISTS `conference_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conference_reservation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `guardian_id` bigint(20) NOT NULL,
  `conference_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3puuypvma9u4nv9mckr4viuqf` (`conference_id`),
  CONSTRAINT `FK3puuypvma9u4nv9mckr4viuqf` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference_reservation`
--

LOCK TABLES `conference_reservation` WRITE;
/*!40000 ALTER TABLE `conference_reservation` DISABLE KEYS */;
INSERT INTO `conference_reservation` VALUES (38,1,20),(39,2,20),(40,1,21),(41,2,21),(42,24,21),(43,1,22),(44,2,22),(45,24,22),(46,1,23),(47,2,23),(48,24,23),(49,1,24),(50,2,24),(51,24,24),(52,1,25),(53,2,25),(54,24,25),(55,1,26),(56,2,26),(57,24,26),(58,31,27);
/*!40000 ALTER TABLE `conference_reservation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 12:40:10
