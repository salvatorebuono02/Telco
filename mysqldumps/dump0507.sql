-- MySQL dump 10.13  Distrib 8.0.29, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: telco
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `employee_username_uindex` (`username`),
  UNIQUE KEY `employee_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'emp','emp');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fixedinternetservice`
--

DROP TABLE IF EXISTS `fixedinternetservice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fixedinternetservice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_servicePkg` int NOT NULL,
  `numOfGiga` int NOT NULL,
  `feeForExtraGiga` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_servicePkg` (`id_servicePkg`),
  CONSTRAINT `fixedinternetservice_ibfk_1` FOREIGN KEY (`id_servicePkg`) REFERENCES `service_package` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixedinternetservice`
--

LOCK TABLES `fixedinternetservice` WRITE;
/*!40000 ALTER TABLE `fixedinternetservice` DISABLE KEYS */;
INSERT INTO `fixedinternetservice` VALUES (1,1,5,40);
/*!40000 ALTER TABLE `fixedinternetservice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fixedphoneservice`
--

DROP TABLE IF EXISTS `fixedphoneservice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fixedphoneservice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_servicePkg` int NOT NULL,
  `numOfMinutes` int NOT NULL,
  `numOfSms` int NOT NULL,
  `feeExtraMin` int NOT NULL,
  `feeExtraSms` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_servicePkg` (`id_servicePkg`),
  CONSTRAINT `fixedphoneservice_ibfk_1` FOREIGN KEY (`id_servicePkg`) REFERENCES `service_package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixedphoneservice`
--

LOCK TABLES `fixedphoneservice` WRITE;
/*!40000 ALTER TABLE `fixedphoneservice` DISABLE KEYS */;
/*!40000 ALTER TABLE `fixedphoneservice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobileinternetservice`
--

DROP TABLE IF EXISTS `mobileinternetservice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobileinternetservice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_servicePkg` int NOT NULL,
  `numOfGiga` int NOT NULL,
  `feeForExtraGiga` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_servicePkg` (`id_servicePkg`),
  CONSTRAINT `mobileinternetservice_ibfk_1` FOREIGN KEY (`id_servicePkg`) REFERENCES `service_package` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobileinternetservice`
--

LOCK TABLES `mobileinternetservice` WRITE;
/*!40000 ALTER TABLE `mobileinternetservice` DISABLE KEYS */;
INSERT INTO `mobileinternetservice` VALUES (1,1,20,2);
/*!40000 ALTER TABLE `mobileinternetservice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobilephoneservice`
--

DROP TABLE IF EXISTS `mobilephoneservice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobilephoneservice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_servicePkg` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_servicePkg` (`id_servicePkg`),
  CONSTRAINT `mobilephoneservice_ibfk_1` FOREIGN KEY (`id_servicePkg`) REFERENCES `service_package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobilephoneservice`
--

LOCK TABLES `mobilephoneservice` WRITE;
/*!40000 ALTER TABLE `mobilephoneservice` DISABLE KEYS */;
/*!40000 ALTER TABLE `mobilephoneservice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openjpa_sequence_table`
--

DROP TABLE IF EXISTS `openjpa_sequence_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `openjpa_sequence_table` (
  `ID` tinyint NOT NULL,
  `SEQUENCE_VALUE` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openjpa_sequence_table`
--

LOCK TABLES `openjpa_sequence_table` WRITE;
/*!40000 ALTER TABLE `openjpa_sequence_table` DISABLE KEYS */;
INSERT INTO `openjpa_sequence_table` VALUES (0,251);
/*!40000 ALTER TABLE `openjpa_sequence_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_of_subscription` date DEFAULT NULL,
  `date_of_creation` date DEFAULT NULL,
  `hour_of_creation` int DEFAULT NULL,
  `total_value` int NOT NULL,
  `creator` int NOT NULL,
  `confirmed` tinyint(1) DEFAULT NULL,
  `serviceId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orderBy_id` (`creator`),
  KEY `order_service_package_id_fk` (`serviceId`),
  CONSTRAINT `order_service_package_id_fk` FOREIGN KEY (`serviceId`) REFERENCES `service_package` (`id`),
  CONSTRAINT `orderBy` FOREIGN KEY (`creator`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `monthly_fee` int NOT NULL,
  `validityPeriod` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'iphone 13',35,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_package`
--

DROP TABLE IF EXISTS `service_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `validityPeriodId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `service_package_user_id_fk` (`userId`),
  KEY `service_package_validityperiod_id_fk` (`validityPeriodId`),
  CONSTRAINT `service_package_user_id_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `service_package_validityperiod_id_fk` FOREIGN KEY (`validityPeriodId`) REFERENCES `validityperiod` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_package`
--

LOCK TABLES `service_package` WRITE;
/*!40000 ALTER TABLE `service_package` DISABLE KEYS */;
INSERT INTO `service_package` VALUES (1,'package1',1,1),(2,'package2',NULL,1),(201,'ciao',2,2);
/*!40000 ALTER TABLE `service_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_package_product`
--

DROP TABLE IF EXISTS `service_package_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_package_product` (
  `SERVICEPACKAGES_ID` int DEFAULT NULL,
  `PRODUCTS_ID` int DEFAULT NULL,
  KEY `I_SRVCDCT_ELEMENT` (`PRODUCTS_ID`),
  KEY `I_SRVCDCT_SERVICEPACKAGES_ID` (`SERVICEPACKAGES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_package_product`
--

LOCK TABLES `service_package_product` WRITE;
/*!40000 ALTER TABLE `service_package_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_package_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `insolvent` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'salvatore','buono','tottobuono','0807mary',NULL),(2,NULL,NULL,'ciao','ciao',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validityperiod`
--

DROP TABLE IF EXISTS `validityperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `validityperiod` (
  `id` int NOT NULL AUTO_INCREMENT,
  `monthly_fee` int DEFAULT NULL,
  `numOfMonths` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validityperiod`
--

LOCK TABLES `validityperiod` WRITE;
/*!40000 ALTER TABLE `validityperiod` DISABLE KEYS */;
INSERT INTO `validityperiod` VALUES (1,20,12),(2,18,24),(3,15,36);
/*!40000 ALTER TABLE `validityperiod` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-05 18:29:08
