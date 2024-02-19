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
-- Table structure for table `user_link`
--

DROP TABLE IF EXISTS `user_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `agreement` enum('S','A','D') COLLATE utf8mb4_bin NOT NULL,
  `relationship` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `target_id` bigint(20) NOT NULL,
  `application_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_link_unique` (`permission_id`,`target_id`,`application_id`),
  KEY `FKm78y42dthtohlmai8ggi76ja6` (`target_id`),
  KEY `FK2invjrgvbqkywmjeg408nfkj` (`application_id`),
  CONSTRAINT `FK2invjrgvbqkywmjeg408nfkj` FOREIGN KEY (`application_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKm78y42dthtohlmai8ggi76ja6` FOREIGN KEY (`target_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKp7sm5ahxtlwjssm7nnhl5ypld` FOREIGN KEY (`permission_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_link`
--

LOCK TABLES `user_link` WRITE;
/*!40000 ALTER TABLE `user_link` DISABLE KEYS */;
INSERT INTO `user_link` VALUES (2,'A','손자',3,1,4),(3,'A','딸',3,2,4),(14,'A','아들',3,24,4),(15,'A','아들',30,31,29);
/*!40000 ALTER TABLE `user_link` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 12:39:57
