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
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `caregiver_id` bigint(20) DEFAULT NULL,
  `report_month` int(11) DEFAULT NULL,
  `report_info` longtext COLLATE utf8mb4_bin DEFAULT NULL,
  `report_year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES (1,3,10,'{\"year\":2023,\"month\":10,\"lifeInfo\":null,\"movie\":null,\"message\":null,\"pressure\":{\"insights\":{\"early\":\"혈압 수치는 위험 범위 밖에 있지 않습니다. 현재는 특별한 조치가 필요하지 않습니다.\",\"mid\":\"혈압 수치에 큰 변동이 없으며 안정적인 상태를 유지하고 있습니다.\",\"late\":\"혈압 수치는 정상 범위에 있으며 건강 상태가 좋습니다.\"},\"data\":[{\"day\":\"2021-01-01\",\"low\":124,\"high\":135},{\"day\":\"2021-01-06\",\"low\":129,\"high\":134},{\"day\":\"2021-01-11\",\"low\":123,\"high\":137},{\"day\":\"2021-01-16\",\"low\":125,\"high\":133},{\"day\":\"2021-01-21\",\"low\":128,\"high\":134},{\"day\":\"2021-01-26\",\"low\":124,\"high\":131}]},\"sugar\":{\"insights\":{\"early\":\"혈당 수치의 변동이 크며, 공복 혈당 수치가 높은 편입니다. 생활 습관과 식단 조절을 통해 관리가 필요합니다.\",\"mid\":\"혈당 수치가 안정적이며, 공복 혈당 수치도 정상 범위에 있습니다. 현재 상태를 유지하면 됩니다.\",\"late\":\"혈당 수치의 변동이 크지 않고, 혈당 수치가 정상 범위에 있습니다. 건강 상태가 좋습니다.\"},\"data\":[{\"day\":\"2021-01-01\",\"low\":78,\"high\":120},{\"day\":\"2021-01-06\",\"low\":84,\"high\":118},{\"day\":\"2021-01-11\",\"low\":78,\"high\":115},{\"day\":\"2021-01-16\",\"low\":83,\"high\":120},{\"day\":\"2021-01-21\",\"low\":90,\"high\":120},{\"day\":\"2021-01-26\",\"low\":91,\"high\":109}]},\"condition\":{\"good\":0,\"normal\":0,\"bad\":0}}',2023),(9,3,1,'{\"year\":2024,\"month\":1,\"permission\":\"하늘요양원\",\"target\":\"이순자\",\"lifeInfo\":\"입소자분은 이번 달에 가정사진을 보며 즐거운 시간을 보내셨고, 그룹 활동에 많이 참여하셨습니다. 건강 지표는 모두 안정적으로 나타났습니다.\",\"movie\":\"3b2d8c8c-34d4-4ab7-8f23-d3ffa72f1b80.mp4\",\"message\":\"갑진년 새해복<br/> 많이 받으세요\",\"pressure\":{\"insights\":{\"early\":\"입소자분의 혈압은 이른 시기에 약간 높았으나, 아직 큰 우려는 없습니다. 그러나 식이 조절 및 건강한 생활습관이 권장됩니다.\",\"mid\":\"입소자분의 혈압은 중간 시기에 안정적으로 유지되었습니다. 그러나 유의미한 변동이 없으므로 신체활동과 건강한 식단을 유지할 것을 권장합니다.\",\"late\":\"입소자분의 혈압은 말기 시기에 높아졌습니다. 이는 식이 습관이나 스트레스와 관련될 수 있습니다. 식이 조절 및 생활 습관 개선이 필요합니다.\"},\"data\":[{\"day\":\"2021-01-01\",\"low\":78,\"high\":129},{\"day\":\"2021-01-06\",\"low\":81,\"high\":129},{\"day\":\"2021-01-11\",\"low\":78,\"high\":94},{\"day\":\"2021-01-16\",\"low\":87,\"high\":97},{\"day\":\"2021-01-21\",\"low\":93,\"high\":156},{\"day\":\"2021-01-26\",\"low\":100,\"high\":178}]},\"sugar\":{\"insights\":{\"early\":\"입소자분의 혈당은 이른 시기에 약간 높았으나, 아직 큰 우려는 없습니다. 그러나 식이 조절 및 건강한 생활습관이 권장됩니다.\",\"mid\":\"입소자분의 혈당은 중간 시기에 안정적으로 유지되었습니다. 그러나 유의미한 변동이 없으므로 신체활동과 건강한 식단을 유지할 것을 권장합니다.\",\"late\":\"입소자분의 혈당은 말기 시기에 조금 높아졌습니다. 이는 식이 습관이나 스트레스와 관련될 수 있습니다. 식이 조절 및 생활 습관 개선이 필요합니다.\"},\"data\":[{\"day\":\"2021-01-01\",\"low\":71,\"high\":165},{\"day\":\"2021-01-06\",\"low\":71,\"high\":157},{\"day\":\"2021-01-11\",\"low\":88,\"high\":138},{\"day\":\"2021-01-16\",\"low\":71,\"high\":127},{\"day\":\"2021-01-21\",\"low\":94,\"high\":145},{\"day\":\"2021-01-26\",\"low\":103,\"high\":166}]},\"condition\":{\"good\":10,\"normal\":12,\"bad\":9}}',2024),(11,3,2,'{\"year\":2024,\"month\":1,\"permission\":\"하늘요양원\",\"target\":\"이순자\",\"lifeInfo\":\"입소자분은 이번 달에 가정사진을 보며 즐거운 시간을 보내셨고, 그룹 활동에 많이 참여하셨습니다. 건강 지표는 모두 안정적으로 나타났습니다.\",\"movie\":\"3b6210c6-f57a-4012-8c87-51d9decdc2a0.mp4\",\"message\":\"갑진년 새해복<br/> 많이 받으세요\",\"pressure\":{\"insights\":{\"early\":\"입소자분의 혈압은 이른 시기에 약간 높았으나, 아직 큰 우려는 없습니다. 그러나 식이 조절 및 건강한 생활습관이 권장됩니다.\",\"mid\":\"입소자분의 혈압은 중간 시기에 안정적으로 유지되었습니다. 그러나 유의미한 변동이 없으므로 신체활동과 건강한 식단을 유지할 것을 권장합니다.\",\"late\":\"입소자분의 혈압은 말기 시기에 높아졌습니다. 이는 식이 습관이나 스트레스와 관련될 수 있습니다. 식이 조절 및 생활 습관 개선이 필요합니다.\"},\"data\":[{\"day\":\"2021-01-01\",\"low\":78,\"high\":129},{\"day\":\"2021-01-06\",\"low\":81,\"high\":129},{\"day\":\"2021-01-11\",\"low\":78,\"high\":94},{\"day\":\"2021-01-16\",\"low\":87,\"high\":97},{\"day\":\"2021-01-21\",\"low\":93,\"high\":156},{\"day\":\"2021-01-26\",\"low\":100,\"high\":178}]},\"sugar\":{\"insights\":{\"early\":\"입소자분의 혈당은 이른 시기에 약간 높았으나, 아직 큰 우려는 없습니다. 그러나 식이 조절 및 건강한 생활습관이 권장됩니다.\",\"mid\":\"입소자분의 혈당은 중간 시기에 안정적으로 유지되었습니다. 그러나 유의미한 변동이 없으므로 신체활동과 건강한 식단을 유지할 것을 권장합니다.\",\"late\":\"입소자분의 혈당은 말기 시기에 조금 높아졌습니다. 이는 식이 습관이나 스트레스와 관련될 수 있습니다. 식이 조절 및 생활 습관 개선이 필요합니다.\"},\"data\":[{\"day\":\"2021-01-01\",\"low\":71,\"high\":165},{\"day\":\"2021-01-06\",\"low\":71,\"high\":157},{\"day\":\"2021-01-11\",\"low\":88,\"high\":138},{\"day\":\"2021-01-16\",\"low\":71,\"high\":127},{\"day\":\"2021-01-21\",\"low\":94,\"high\":145},{\"day\":\"2021-01-26\",\"low\":103,\"high\":166}]},\"condition\":{\"good\":10,\"normal\":12,\"bad\":9}}',2024);
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 12:39:56
