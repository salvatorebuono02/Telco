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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
INSERT INTO `alert` VALUES (2,NULL,NULL,432,'2022-07-11',5),(2,NULL,NULL,240,'2022-07-11',6);
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addAlert` AFTER INSERT ON `alert` FOR EACH ROW begin
    insert into alertsForReport
        values (NEW.id);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
INSERT INTO `alertsforreport` VALUES (5),(6);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'emp','emp'),(2,'employee','test');
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
INSERT INTO `openjpa_sequence_table` VALUES (0,451);
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
  `totalvalueservices` float DEFAULT NULL,
  `totalvalueproducts` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orderBy_id` (`creator`),
  KEY `order_service_package_id_fk` (`serviceId`),
  KEY `order_validityperiod_id_fk` (`validityId`),
  CONSTRAINT `order_service_package_id_fk` FOREIGN KEY (`serviceId`) REFERENCES `service_package` (`id`),
  CONSTRAINT `order_validityperiod_id_fk` FOREIGN KEY (`validityId`) REFERENCES `validityperiod` (`id`),
  CONSTRAINT `orderBy` FOREIGN KEY (`creator`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (74,'2022-07-16','2022-07-11',2,1,401,2,467,'2024-07-16',432,35),(77,'2022-07-15','2022-07-11',2,1,351,2,432,'2024-07-15',432,0),(78,'2022-07-08','2022-07-11',2,0,1,1,240,'2023-07-08',240,0);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addPurchaseToPackage` AFTER INSERT ON `order` FOR EACH ROW begin
        if NEW.confirmed=true then
            update totalpurchaseperpackage set totalPurchases=totalPurchases+1
            where package_id in (select o.serviceId
                                 from `order` o
                                 where o.serviceId=NEW.serviceId);
        end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addPurchaseToPackageAndValPeriod` AFTER INSERT ON `order` FOR EACH ROW begin
    if NEW.confirmed=true then
        update totalPurchasePerPackAndValidityPeriod set totalPurchases=totalPurchases+1
        where (package_id,valPeriod_id) in (select o.serviceId,o.validityId
                                            from `order` o
                                            where o.serviceId=NEW.serviceId);
    end if;

end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addSales` AFTER INSERT ON `order` FOR EACH ROW begin
    declare x,y float;
    if NEW.confirmed=true then
        select o.totalvalueservices,o.totalvalueproducts into x,y
        from `order` o
        where o.serviceId=NEW.serviceId;
        update salesPackage s
        set s.totalSalesWithProduct=s.totalSalesWithProduct+x+y, s.totalSalesWithoutProduct=s.totalSalesWithoutProduct+x
        where s.package_id in (select o.serviceId
                               from `order` o
                               where o.serviceId=NEW.serviceId);
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `createSales` AFTER INSERT ON `order` FOR EACH ROW begin
        if NEW.confirmed=true then
            insert into salesPackage(package_id)
            values (NEW.serviceId);
        end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addOptProduct` AFTER INSERT ON `order` FOR EACH ROW begin
    if NEW.confirmed=true then
        delete from totalnumberofoptionalproduct t
        where t.package_id in (select s.id
                               from service_package s
                               where s.id=NEW.serviceId);
        insert into totalnumberofoptionalproduct
            select sp.id, count(*)
                from `order` as o
                join order_product op on o.id = op.order_id
                join service_package sp on sp.id = o.serviceId
                where o.confirmed=true
                group by sp.id;

       delete from avgnumofproductsperpackage
           where package_id in (select s.id
                                from service_package s
                                where s.id=NEW.serviceId);
       insert into avgnumofproductsperpackage
           select t.package_id,ifnull((o.total/t.totalPurchases),0.0)
               from totalpurchaseperpackage t left outer join totalnumberofoptionalproduct o on t.package_id=o.package_id
               where t.package_id in (select sp.id
                                      from service_package sp
                                      where sp.id=NEW.serviceId);
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addSuspendedOrder` AFTER INSERT ON `order` FOR EACH ROW begin
    if(NEW.confirmed=false) then
        if(NEW.id not in (select order_id from suspendedorders)) then
            insert into suspendedorders
                select o.id
                    from `order` o
                    where NEW.id=o.id;
        end if;
    else
            if(NEW.id in (select order_id from suspendedorders)) then
                delete from suspendedorders s
                where s.order_id=NEW.id;
            end if;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `addSalesForEachProduct` AFTER INSERT ON `order` FOR EACH ROW begin
    declare y float;
    declare id int;
    if NEW.confirmed=true then
        select p.id,(p.monthly_fee*v.numOfMonths) into id,y
            from product p join order_product op on p.id = op.order_id
                join `order` o on op.order_id = o.id
                join validityperiod v on v.id = o.validityId
        where o.id=NEW.id
        group by p.id;
        update salesForEachOptproduct s
        set s.sales=s.sales+y
        where s.optionalProduct_id = id;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updatePurchaseToPackage` AFTER UPDATE ON `order` FOR EACH ROW begin
    if NEW.confirmed=true then
        update totalpurchaseperpackage set totalPurchases=totalPurchases+1
        where package_id in (select o.serviceId
                             from `order` o
                             where o.serviceId=NEW.serviceId);
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updatePurchaseToPackageAndValPeriod` AFTER UPDATE ON `order` FOR EACH ROW begin
    if NEW.confirmed=true then
        update totalPurchasePerPackAndValidityPeriod set totalPurchases=totalPurchases+1
        where (package_id,valPeriod_id) in (select o.serviceId,o.validityId
                                            from `order` o
                                            where o.serviceId=NEW.serviceId);
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updateSales` AFTER UPDATE ON `order` FOR EACH ROW begin
        declare x,y float;
        if NEW.confirmed=true then
            select o.totalvalueservices,o.totalvalueproducts into x,y
            from `order` o
            where o.serviceId=NEW.serviceId;
            update salesPackage s
            set s.totalSalesWithProduct=s.totalSalesWithProduct+x+y, s.totalSalesWithoutProduct=s.totalSalesWithoutProduct+x
            where s.package_id in (select o.serviceId
                                   from `order` o
                                   where o.serviceId=NEW.serviceId);
        end if;
    end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updateOptProduct` AFTER UPDATE ON `order` FOR EACH ROW begin
    if NEW.confirmed=true then
        delete from totalnumberofoptionalproduct t
        where t.package_id in (select s.id
                               from service_package s
                               where s.id=NEW.serviceId);
        insert into totalnumberofoptionalproduct
        select sp.id, count(*)
        from `order` as o
                 join order_product op on o.id = op.order_id
                 join service_package sp on sp.id = o.serviceId
        where o.confirmed=true
        group by sp.id;

        delete from avgnumofproductsperpackage
        where package_id in (select s.id
                             from service_package s
                             where s.id=NEW.serviceId);
        insert into avgnumofproductsperpackage
        select t.package_id,ifnull((o.total/t.totalPurchases),0.0)
        from totalpurchaseperpackage t left outer join totalnumberofoptionalproduct o on t.package_id=o.package_id
        where t.package_id in (select sp.id
                               from service_package sp
                               where sp.id=NEW.serviceId);
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updateSuspendedOrder` AFTER UPDATE ON `order` FOR EACH ROW begin
    if(NEW.confirmed=false) then
        if(NEW.id not in (select order_id from suspendedorders)) then
            insert into suspendedorders
            select o.id
            from `order` o
            where NEW.id=o.id;
        end if;
    else
        if(NEW.id in (select order_id from suspendedorders)) then
            delete from suspendedorders s
            where s.order_id=NEW.id;
        end if;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updateSalesForEachProduct` AFTER UPDATE ON `order` FOR EACH ROW begin
    declare y float;
    declare id int;
    if NEW.confirmed=true then
        select p.id,(p.monthly_fee*v.numOfMonths) into id,y
        from product p join order_product op on p.id = op.order_id
                       join `order` o on op.order_id = o.id
                       join validityperiod v on v.id = o.validityId        where o.id=NEW.id
        group by p.id;
        update salesForEachOptproduct s
        set s.sales=s.sales+y
        where s.optionalProduct_id = id;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `order_product`
--

DROP TABLE IF EXISTS `order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_product` (
  `product_id` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  KEY `order_product_order_id_fk` (`order_id`),
  KEY `order_product_product_id_fk` (`product_id`),
  CONSTRAINT `order_product_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `order_product_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product`
--

LOCK TABLES `order_product` WRITE;
/*!40000 ALTER TABLE `order_product` DISABLE KEYS */;
INSERT INTO `order_product` VALUES (1,74);
/*!40000 ALTER TABLE `order_product` ENABLE KEYS */;
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
INSERT INTO `salespackage` VALUES (351,0,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=402 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_package`
--

LOCK TABLES `service_package` WRITE;
/*!40000 ALTER TABLE `service_package` DISABLE KEYS */;
INSERT INTO `service_package` VALUES (1,'package1',1,1),(2,'package2',NULL,2),(202,'package3',NULL,2),(351,'allallero',NULL,2),(401,'aaaaaa',NULL,2);
/*!40000 ALTER TABLE `service_package` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `createOrderAndAssociatedServicePackage` AFTER INSERT ON `service_package` FOR EACH ROW begin
    insert into totalpurchaseperpackage(package_id)
    values (NEW.id);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `createOrderAndAssociatedServicePackageAndValPeriod` AFTER INSERT ON `service_package` FOR EACH ROW begin
    insert into totalPurchasePerPackAndValidityPeriod(package_id, valPeriod_id)
        values (NEW.id,NEW.validityPeriodId);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `createOrder` AFTER INSERT ON `service_package` FOR EACH ROW begin
    insert into avgnumofproductsperpackage(package_id)
        values(NEW.id);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
INSERT INTO `service_package_product` VALUES (401,1);
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
INSERT INTO `suspendedorders` VALUES (78);
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
INSERT INTO `totalnumberofoptionalproduct` VALUES (401,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'salvatore','buono','tottobuono','0807mary',0,0),(2,NULL,NULL,'ciao','ciao',0,4),(3,NULL,NULL,'test','test',0,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updateInsolventUser` AFTER UPDATE ON `user` FOR EACH ROW begin
    if NEW.insolvent=true then
        if(NEW.id not in (select user_id from insolventuser)) then
            insert into insolventuser
                values (NEW.id);
        end if;
    else
            delete from insolventuser i
            where i.user_id=NEW.id;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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

-- Dump completed on 2022-07-11 11:21:53
