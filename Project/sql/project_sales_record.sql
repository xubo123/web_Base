-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	5.7.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sales_record`
--

DROP TABLE IF EXISTS `sales_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales_record` (
  `id` int(11) NOT NULL,
  `contract_id` varchar(45) NOT NULL,
  `contract_source` varchar(45) NOT NULL,
  `apply_id` varchar(45) NOT NULL,
  `trail_produce_id` varchar(45) NOT NULL,
  `supplier` varchar(45) NOT NULL,
  `person_in_charge` varchar(45) DEFAULT NULL,
  `acceptor` varchar(45) DEFAULT NULL,
  `price_calculator` varchar(45) DEFAULT NULL,
  `settle_accountor` varchar(45) DEFAULT NULL,
  `component_id` varchar(45) NOT NULL,
  `component_name` varchar(45) NOT NULL,
  `unit_name` varchar(45) NOT NULL,
  `warehouse_num` int(11) NOT NULL,
  `unit_price` int(11) DEFAULT NULL,
  `price_with_tax` decimal(10,2) DEFAULT NULL,
  `setup_price` int(11) DEFAULT NULL,
  `other_price` int(11) DEFAULT NULL,
  `totalprice_with_tax` decimal(10,2) DEFAULT NULL,
  `invoice_num` varchar(45) DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `returned_money` decimal(10,2) DEFAULT NULL,
  `returned_date` date DEFAULT NULL,
  `contract_belongs` varchar(45) DEFAULT NULL,
  `remark` varchar(45) DEFAULT NULL,
  `pay_info` varchar(45) DEFAULT NULL,
  `remit_record` varchar(45) DEFAULT NULL,
  `produce_fee` int(11) DEFAULT NULL,
  `producer` varchar(45) DEFAULT NULL,
  `other_cost` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_record`
--

LOCK TABLES `sales_record` WRITE;
/*!40000 ALTER TABLE `sales_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-12 23:07:45
