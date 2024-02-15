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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_type` varchar(31) COLLATE utf8mb4_bin NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `brith` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `domain_id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `kakao_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `naver_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `refresh_token` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `hospital_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9xpi27fcndxmxx8vla1d1ye4i` (`hospital_id`),
  CONSTRAINT `FK6moygcdg72r9scptm49qwqhsa` FOREIGN KEY (`hospital_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Guardian',1,NULL,'1961-01-01','10damin04',NULL,NULL,NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','010-1111-1111','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMGRhbWluMDQiLCJleHAiOjE3MTA1NTE5OTF9.68U5GyBbSZt9DiiVzYqq6WeTzzX5P6zxe4NDiTwQalQ','조다민',NULL,NULL),('Guardian',2,NULL,'1961-01-01','ssafy2',NULL,NULL,NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','010-1111-1111','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc2FmeTIiLCJleHAiOjE3MTA1NTM4Mjd9.CJomo46ExAhM8ZB_OctBLX2Y0LUel8w169PdWff9Yuk','이보호',NULL,NULL),('Caregiver',3,NULL,'1961-01-01','caregiver',NULL,NULL,NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','010-1111-1111','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJlZ2l2ZXIiLCJleHAiOjE3MTA1NTA5MDl9.tHF9MqF6TXDTy89d4V0_t-DaSq8w0b8KN3wEgGKbY_M','이순자','token',4),('Hospital',4,NULL,'1961-01-01','hospital',NULL,NULL,NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','010-1111-1111','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob3NwaXRhbCIsImV4cCI6MTcwODM5MDI5MH0.dX5D14Uks0J-YXgbwWkIrLM_g2hJ4i7HH-V2RnjPXHA','하늘요양원',NULL,NULL),('Guardian',23,NULL,'2024-02-20','coach01',NULL,NULL,NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','012-1222-2222','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2FjaDAxIiwiZXhwIjoxNzEwNTUxNjE4fQ.AxGie7Vy_dVwdt2ZXp-mfWVl9QY6FJ4ArfNBtBOyNWs','강코',NULL,NULL),('Guardian',24,NULL,'1994-06-16','ssafy10',NULL,'',NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','121-2121-2121',NULL,'김호떡',NULL,NULL),('Guardian',25,NULL,'2000-12-31','test111',NULL,'skagmltn7@hanmail.net',NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','1-1-1','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTExIiwiZXhwIjoxNzEwNDkwMTg3fQ.UrQIhbBN3WG6XDtKJb52M4X6sORtC8olEdrQvxR0Me8','1',NULL,NULL),('Guardian',26,NULL,'+111111-01-01','ssafy1232',NULL,'',NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','fds-sdf-sdfs',NULL,'dfssdfdfd',NULL,NULL),('Guardian',27,NULL,'1999-08-06','damin1',NULL,'',NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','010-3769-8642','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYW1pbjEiLCJleHAiOjE3MTA0OTY1NTR9.BC4z_msAp0Mj4_RNyyVZ8a-V2CcMnt94oES9X2M1X3U','조다민',NULL,NULL),('Guardian',28,NULL,'2001-02-20','rhrnak1',NULL,'',NULL,'{bcrypt}$2a$10$Mie/mnapGsC/H9B.s2OvYexRI7PGNnQmSKVG0d.JARXMa5X7IDIDe','010-3334-0123','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaHJuYWsxIiwiZXhwIjoxNzEwNTUwNzI3fQ.KOQ3kJ2Yy7WP154kpuN_lxwPl-SknL0g-mikR3q-hTM','고구마',NULL,NULL),('Hospital',29,NULL,'1961-01-01','hospital_coach',NULL,NULL,NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','010-1111-1111',NULL,'싸피요양원',NULL,NULL),('Caregiver',30,NULL,'1961-01-01','caregiver_coach',NULL,NULL,NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','010-2222-2222','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJlZ2l2ZXJfY29hY2giLCJleHAiOjE3MTA1NTU4MjN9.niRRFjHYtTV9uw6jrXZ61bcb522ABuXHzWpHjHhUTII','컨설턴트님','ssafy',29),('Guardian',31,NULL,'2000-07-07','guardian_coach',NULL,NULL,NULL,'{bcrypt}$2a$10$wOdl6fr6et8IF4IWpoqNTufQ.CUYUnNSdUfUjGjqTgs8xClkLasXu','010-3333-3333','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWFyZGlhbl9jb2FjaCIsImV4cCI6MTcxMDU1NTgxN30.HD0pOtJfTevolILc5mEso6Muxos7_KLlRpdq8VFhGoo','코치님',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 12:40:07
