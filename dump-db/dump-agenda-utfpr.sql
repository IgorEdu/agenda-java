-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: agenda-utfpr
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `agendas`
--

DROP TABLE IF EXISTS `agendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agendas` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint unsigned NOT NULL,
  `nome` varchar(50) NOT NULL,
  `descricao` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `agendas_usuarios_FK` (`id_usuario`),
  CONSTRAINT `agendas_usuarios_FK` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agendas`
--

LOCK TABLES `agendas` WRITE;
/*!40000 ALTER TABLE `agendas` DISABLE KEYS */;
INSERT INTO `agendas` VALUES (2,4,'Agenda de teste','Descrição da agenda usada para teste do método'),(3,4,'Agenda de teste','Descrição da agenda usada para teste do método'),(4,4,'Agenda de teste','Descrição da agenda usada para teste do método'),(5,4,'Agenda de teste','Descrição da agenda usada para teste do método'),(11,7,'nova','agenda nova');
/*!40000 ALTER TABLE `agendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compromissos`
--

DROP TABLE IF EXISTS `compromissos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compromissos` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `descricao` varchar(500) DEFAULT NULL,
  `data_inicio` date NOT NULL,
  `horario_inicio` time DEFAULT NULL,
  `data_termino` date NOT NULL,
  `horario_termino` time NOT NULL,
  `local` varchar(100) DEFAULT NULL,
  `data_notificacao` date DEFAULT NULL,
  `horario_notificacao` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compromissos`
--

LOCK TABLES `compromissos` WRITE;
/*!40000 ALTER TABLE `compromissos` DISABLE KEYS */;
INSERT INTO `compromissos` VALUES (3,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','13:30:59','2024-05-05','12:30:00','Sala de reuniões',NULL,NULL),(4,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(5,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(6,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(7,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(8,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(9,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(10,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(11,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(12,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(13,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL),(14,'Compromisso teste','Compromisso usado para testar a classe DAO','2024-05-05','12:30:59','2024-05-05','11:30:00','Sala de reuniões',NULL,NULL);
/*!40000 ALTER TABLE `compromissos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compromissos_agendas`
--

DROP TABLE IF EXISTS `compromissos_agendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compromissos_agendas` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `id_compromissos` bigint unsigned NOT NULL,
  `id_agendas` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `compromissos_agendas_compromissos_FK` (`id_compromissos`),
  KEY `compromissos_agendas_agendas_FK` (`id_agendas`),
  CONSTRAINT `compromissos_agendas_agendas_FK` FOREIGN KEY (`id_agendas`) REFERENCES `agendas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `compromissos_agendas_compromissos_FK` FOREIGN KEY (`id_compromissos`) REFERENCES `compromissos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compromissos_agendas`
--

LOCK TABLES `compromissos_agendas` WRITE;
/*!40000 ALTER TABLE `compromissos_agendas` DISABLE KEYS */;
/*!40000 ALTER TABLE `compromissos_agendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `convites`
--

DROP TABLE IF EXISTS `convites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `convites` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(100) NOT NULL,
  `id_compromisso` bigint unsigned DEFAULT NULL,
  `id_convidado` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `convites_compromissos_FK` (`id_compromisso`),
  KEY `convites_usuarios_FK` (`id_convidado`),
  CONSTRAINT `convites_compromissos_FK` FOREIGN KEY (`id_compromisso`) REFERENCES `compromissos` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `convites_usuarios_FK` FOREIGN KEY (`id_convidado`) REFERENCES `usuarios` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `convites`
--

LOCK TABLES `convites` WRITE;
/*!40000 ALTER TABLE `convites` DISABLE KEYS */;
/*!40000 ALTER TABLE `convites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `senha` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nome` varchar(50) NOT NULL,
  `nascimento` date NOT NULL,
  `genero` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `caminho_imagem` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (4,'teste 1','agsasrqwfqwwqwv','usuario teste','2024-06-13','MASCULINO','emailteste@email.com',NULL),(7,'novo','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3','novo usuario','1999-01-01','Masculino','novo@novo.com',NULL),(8,'igorzinho','A48E7CEB4415311590CA6BA9C2A5F1D337489CCE1DC6D38B2E4B12A6AF5D827C','Igor Eduardo','2024-06-14','Masculino','igor@gmail.com',NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'agenda-utfpr'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-18 21:02:05
