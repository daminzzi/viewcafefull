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
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `information` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `medicine_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (1,'두통, 발열에 효과적','타이레놀'),(2,'두통, 생리통, 치통에 사용','게보린'),(3,'면역력 강화에 도움','비타민C 1000'),(4,'혈액 희석제, 심혈관 질환 예방','아스피린'),(5,'감기 초기 증상 완화','콜드펙스'),(6,'장 건강 및 소화기능 개선','프로바이오틱스'),(7,'알레르기 증상 완화','로페나민'),(8,'위산 과다, 위염 증상 완화','잔탁'),(9,'열감기 증상에 사용','판콜'),(10,'수면의 질 향상','멜라토닌'),(11,'코감기, 코막힘 해소','엑세라민'),(12,'관절염 증상 완화, 연골 보호','글루코사민'),(13,'수면 유도 및 불면증 개선','나이트민'),(14,'소염진통제, 근육통 완화','이부프로펜'),(15,'진통제, 해열제로 사용','파라세타몰'),(16,'복통, 경련 완화','노스파'),(17,'소화불량, 위장질환 치료','모사프리드'),(18,'알레르기성 비염, 두드러기 치료','지르텍'),(19,'계절성 알레르기 증상 완화','알레그라'),(20,'근육 기능 개선, 변비 예방','마그네슘'),(21,'심혈관 건강 증진','오메가3'),(22,'위궤양, 위염 치료','레바미피드'),(23,'관절염, 성인류마티즘 치료','셀레콕시브'),(24,'콜레스테롤 수치 감소','심바스타틴'),(25,'2형 당뇨병 치료','메트포르민'),(26,'혈전증 예방','리바록사반'),(27,'고혈압 치료','암로디핀'),(28,'알레르기 증상 치료','세티리진'),(29,'고혈압, 심부전 치료','펜토프릴'),(30,'위산 분비 억제, 속쓰림 치료','라니티딘');
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 12:40:00
