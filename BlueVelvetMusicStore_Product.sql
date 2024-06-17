-- MySQL dump 10.13  Distrib 8.0.37, for Linux (x86_64)
--
-- Host: localhost    Database: BlueVelvetMusicStore
-- ------------------------------------------------------
-- Server version	8.0.37-0ubuntu0.22.04.3

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
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `short_description` text,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `list_price` decimal(10,2) NOT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
INSERT INTO `Product` VALUES (43,'Product 1','Short description of product 1','Brand A','Category X',100.00,60.00),(44,'Product 2','Short description of product 2','Brand B','Category Y',150.00,80.00),(45,'Product 3','Short description of product 3','Brand C','Category Z',200.00,120.00),(46,'Product 4','Short description of product 4','Brand D','Category X',250.00,150.00),(47,'Product 5','Short description of product 5','Brand E','Category Y',300.00,180.00),(48,'Product 6','Short description of product 6','Brand F','Category Z',350.00,210.00),(49,'Product 7','Short description of product 7','Brand G','Category X',400.00,240.00),(50,'Product 8','Short description of product 8','Brand H','Category Y',450.00,270.00),(51,'Product 9','Short description of product 9','Brand I','Category Z',500.00,300.00),(52,'Product 10','Short description of product 10','Brand J','Category X',550.00,330.00),(57,'Product 1','Short description of product 1','Brand A','Category X',100.00,60.00),(58,'Product 2','Short description of product 2','Brand B','Category Y',150.00,80.00),(59,'Product 3','Short description of product 3','Brand C','Category Z',200.00,120.00),(60,'Product 4','Short description of product 4','Brand D','Category X',250.00,150.00),(61,'Product 5','Short description of product 5','Brand E','Category Y',300.00,180.00),(62,'Product 6','Short description of product 6','Brand F','Category Z',350.00,210.00),(63,'Product 7','Short description of product 7','Brand G','Category X',400.00,240.00),(64,'Product 8','Short description of product 8','Brand H','Category Y',450.00,270.00),(65,'Product 9','Short description of product 9','Brand I','Category Z',500.00,300.00),(66,'Product 10','Short description of product 10','Brand J','Category X',550.00,330.00),(67,'Product 1','Short description of product 1','Brand A','Category X',100.00,60.00),(68,'Product 2','Short description of product 2','Brand B','Category Y',150.00,80.00),(69,'Product 3','Short description of product 3','Brand C','Category Z',200.00,120.00),(70,'Product 4','Short description of product 4','Brand D','Category X',250.00,150.00),(71,'Product 5','Short description of product 5','Brand E','Category Y',300.00,180.00),(72,'Product 6','Short description of product 6','Brand F','Category Z',350.00,210.00),(73,'Product 7','Short description of product 7','Brand G','Category X',400.00,240.00),(74,'Product 8','Short description of product 8','Brand H','Category Y',450.00,270.00),(75,'Product 9','Short description of product 9','Brand I','Category Z',500.00,300.00),(76,'Product 10','Short description of product 10','Brand J','Category X',550.00,330.00);
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-17 17:56:18
