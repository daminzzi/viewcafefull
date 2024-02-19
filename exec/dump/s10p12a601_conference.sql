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
-- Table structure for table `conference`
--

DROP TABLE IF EXISTS `conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_datetime` datetime(6) DEFAULT NULL,
  `last_modified_datetime` datetime(6) DEFAULT NULL,
  `conference_date` date DEFAULT NULL,
  `end_datetime` datetime(6) DEFAULT NULL,
  `room_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `start_datetime` datetime(6) DEFAULT NULL,
  `conference_state` enum('S','A','D') COLLATE utf8mb4_bin DEFAULT NULL,
  `conference_time` time(6) DEFAULT NULL,
  `target_id` bigint(20) NOT NULL,
  `application_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  `created_date_time` datetime(6) DEFAULT NULL,
  `last_modified_date_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkuv8laa8b45gp4o5wqqdl23ae` (`target_id`),
  KEY `FKmob2sjgb6745ca51wekt9qmnf` (`application_id`),
  KEY `FKf6683do25cfg622hv2n3pmrmv` (`permission_id`),
  CONSTRAINT `FKf6683do25cfg622hv2n3pmrmv` FOREIGN KEY (`permission_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKkuv8laa8b45gp4o5wqqdl23ae` FOREIGN KEY (`target_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmob2sjgb6745ca51wekt9qmnf` FOREIGN KEY (`application_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference`
--

LOCK TABLES `conference` WRITE;
/*!40000 ALTER TABLE `conference` DISABLE KEYS */;
INSERT INTO `conference` VALUES (20,'2024-02-13 05:35:59.048768','2024-02-13 07:10:23.527682','2024-02-13','2024-02-13 07:10:23.527130','20_caregiver','2024-02-13 07:10:03.661849','A','14:30:00.000000',3,1,4,NULL,NULL),(21,'2024-02-13 13:20:44.966601','2024-02-14 06:38:29.331091','2024-02-14','2024-02-14 06:38:29.330710','21_caregiver','2024-02-14 03:00:28.049736','A','19:00:00.000000',3,1,4,NULL,NULL),(22,'2024-02-14 09:20:53.588926','2024-02-14 09:21:12.007320','2024-02-14',NULL,'22_caregiver',NULL,'A','08:30:00.000000',3,1,4,NULL,NULL),(23,'2024-02-14 09:21:01.766289','2024-02-14 01:13:56.673664','2024-02-14',NULL,'23_caregiver','2024-02-14 01:13:56.673156','A','11:30:00.000000',3,1,4,NULL,NULL),(24,'2024-02-14 01:13:47.062682','2024-02-14 01:13:47.062682','2024-02-15',NULL,NULL,NULL,'S','13:00:00.000000',3,1,4,NULL,NULL),(25,'2024-02-14 06:32:48.328301','2024-02-14 06:32:48.328301','2024-02-15',NULL,NULL,NULL,'S','15:00:00.000000',3,1,4,NULL,NULL),(26,'2024-02-14 07:20:48.504315','2024-02-14 07:20:48.504315','2024-02-16',NULL,NULL,NULL,'S','17:00:00.000000',3,1,4,NULL,NULL),(27,'2024-02-15 02:09:03.599612','2024-02-15 02:09:03.599612','2024-02-15',NULL,'27_caregiver_coach',NULL,'A','11:30:00.000000',30,31,29,NULL,NULL);
/*!40000 ALTER TABLE `conference` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 12:40:01
