-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
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
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `userId` int DEFAULT NULL,
  `userName` varchar(45) DEFAULT NULL,
  `userEmail` varchar(45) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `lastRejection` date DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `alert_id_uindex` (`id`),
  KEY `alert_user_id_fk` (`userId`),
  CONSTRAINT `alert_user_id_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alertsforreport`
--

DROP TABLE IF EXISTS `alertsforreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alertsforreport` (
  `alert_id` int NOT NULL,
  KEY `alert_id` (`alert_id`),
  CONSTRAINT `alertsforreport_ibfk_1` FOREIGN KEY (`alert_id`) REFERENCES `alert` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alertsforreport`
--

LOCK TABLES `alertsforreport` WRITE;
/*!40000 ALTER TABLE `alertsforreport` DISABLE KEYS */;
/*!40000 ALTER TABLE `alertsforreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avgnumofproductsperpackage`
--

DROP TABLE IF EXISTS `avgnumofproductsperpackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avgnumofproductsperpackage` (
  `package_id` int NOT NULL,
  `avg` float NOT NULL DEFAULT '-1',
  PRIMARY KEY (`package_id`),
  CONSTRAINT `avgnumofproductsperpackage_ibfk_1` FOREIGN KEY (`package_id`) REFERENCES `service_package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avgnumofproductsperpackage`
--

LOCK TABLES `avgnumofproductsperpackage` WRITE;
/*!40000 ALTER TABLE `avgnumofproductsperpackage` DISABLE KEYS */;
/*!40000 ALTER TABLE `avgnumofproductsperpackage` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Table structure for table `insolventuser`
--

DROP TABLE IF EXISTS `insolventuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insolventuser` (
  `user_id` int NOT NULL,
  KEY `user_id` (`user_id`),
  CONSTRAINT `insolventuser_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insolventuser`
--

LOCK TABLES `insolventuser` WRITE;
/*!40000 ALTER TABLE `insolventuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `insolventuser` ENABLE KEYS */;
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
  `creator` int DEFAULT NULL,
  `confirmed` tinyint(1) DEFAULT NULL,
  `serviceId` int DEFAULT NULL,
  `validityId` int DEFAULT NULL,
  `totalValueOrder` float DEFAULT NULL,
  `date_end_subscription` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orderBy_id` (`creator`),
  KEY `order_service_package_id_fk` (`serviceId`),
  KEY `order_validityperiod_id_fk` (`validityId`),
  CONSTRAINT `order_service_package_id_fk` FOREIGN KEY (`serviceId`) REFERENCES `service_package` (`id`),
  CONSTRAINT `order_validityperiod_id_fk` FOREIGN KEY (`validityId`) REFERENCES `validityperiod` (`id`),
  CONSTRAINT `orderBy` FOREIGN KEY (`creator`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (60,'2022-06-21','2022-07-10',2,1,1,1,240,'2023-06-21'),(61,'2022-07-13','2022-07-10',2,0,2,2,432,'2024-07-13'),(62,'2022-07-20','2022-07-10',2,0,202,2,432,'2024-07-20');
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
  `orderId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_order_id_fk` (`orderId`),
  CONSTRAINT `product_order_id_fk` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'iphone 13',35,NULL,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesforeachoptproduct`
--

DROP TABLE IF EXISTS `salesforeachoptproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salesforeachoptproduct` (
  `optionalProduct_id` int NOT NULL,
  `sales` float NOT NULL,
  PRIMARY KEY (`optionalProduct_id`),
  CONSTRAINT `salesforeachoptproduct_ibfk_1` FOREIGN KEY (`optionalProduct_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesforeachoptproduct`
--

LOCK TABLES `salesforeachoptproduct` WRITE;
/*!40000 ALTER TABLE `salesforeachoptproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesforeachoptproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salespackage`
--

DROP TABLE IF EXISTS `salespackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salespackage` (
  `package_id` int NOT NULL,
  `totalSalesWithProduct` int NOT NULL DEFAULT '0',
  `totalSalesWithoutProduct` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`package_id`),
  CONSTRAINT `salespackage_ibfk_1` FOREIGN KEY (`package_id`) REFERENCES `service_package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salespackage`
--

LOCK TABLES `salespackage` WRITE;
/*!40000 ALTER TABLE `salespackage` DISABLE KEYS */;
/*!40000 ALTER TABLE `salespackage` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_package`
--

LOCK TABLES `service_package` WRITE;
/*!40000 ALTER TABLE `service_package` DISABLE KEYS */;
INSERT INTO `service_package` VALUES (1,'package1',1,1),(2,'package2',NULL,2),(202,'package3',NULL,2);
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
-- Table structure for table `suspendedorders`
--

DROP TABLE IF EXISTS `suspendedorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suspendedorders` (
  `order_id` int NOT NULL,
  KEY `order_id` (`order_id`),
  CONSTRAINT `suspendedorders_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suspendedorders`
--

LOCK TABLES `suspendedorders` WRITE;
/*!40000 ALTER TABLE `suspendedorders` DISABLE KEYS */;
/*!40000 ALTER TABLE `suspendedorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `totalnumberofoptionalproduct`
--

DROP TABLE IF EXISTS `totalnumberofoptionalproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `totalnumberofoptionalproduct` (
  `package_id` int NOT NULL,
  `total` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`package_id`),
  CONSTRAINT `totalnumberofoptionalproduct_ibfk_1` FOREIGN KEY (`package_id`) REFERENCES `service_package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `totalnumberofoptionalproduct`
--

LOCK TABLES `totalnumberofoptionalproduct` WRITE;
/*!40000 ALTER TABLE `totalnumberofoptionalproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `totalnumberofoptionalproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `totalpurchaseperpackage`
--

DROP TABLE IF EXISTS `totalpurchaseperpackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `totalpurchaseperpackage` (
  `package_id` int NOT NULL,
  `totalPurchases` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`package_id`),
  CONSTRAINT `totalpurchaseperpackage_ibfk_1` FOREIGN KEY (`package_id`) REFERENCES `service_package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `totalpurchaseperpackage`
--

LOCK TABLES `totalpurchaseperpackage` WRITE;
/*!40000 ALTER TABLE `totalpurchaseperpackage` DISABLE KEYS */;
/*!40000 ALTER TABLE `totalpurchaseperpackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `totalpurchaseperpackandvalidityperiod`
--

DROP TABLE IF EXISTS `totalpurchaseperpackandvalidityperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `totalpurchaseperpackandvalidityperiod` (
  `package_id` int NOT NULL,
  `valPeriod_id` int NOT NULL,
  `totalPurchases` int NOT NULL DEFAULT '0',
  KEY `package_id` (`package_id`),
  KEY `valPeriod_id` (`valPeriod_id`),
  CONSTRAINT `totalpurchaseperpackandvalidityperiod_ibfk_1` FOREIGN KEY (`package_id`) REFERENCES `service_package` (`id`),
  CONSTRAINT `totalpurchaseperpackandvalidityperiod_ibfk_2` FOREIGN KEY (`valPeriod_id`) REFERENCES `validityperiod` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `totalpurchaseperpackandvalidityperiod`
--

LOCK TABLES `totalpurchaseperpackandvalidityperiod` WRITE;
/*!40000 ALTER TABLE `totalpurchaseperpackandvalidityperiod` DISABLE KEYS */;
/*!40000 ALTER TABLE `totalpurchaseperpackandvalidityperiod` ENABLE KEYS */;
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
  `insolvent` tinyint(1) DEFAULT NULL,
  `failedPayments` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'salvatore','buono','tottobuono','0807mary',0,0),(2,NULL,NULL,'ciao','ciao',0,2);
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

-- Dump completed on 2022-07-10 19:54:53
