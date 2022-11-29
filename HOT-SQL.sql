-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           8.0.30 - MySQL Community Server - GPL
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para hot
CREATE DATABASE IF NOT EXISTS `hot` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hot`;

-- Copiando estrutura para tabela hot.player
CREATE TABLE IF NOT EXISTS `player` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capacity` float NOT NULL,
  `chaseEnum` varchar(255) DEFAULT NULL,
  `directionEnum` varchar(255) DEFAULT NULL,
  `fightEnum` varchar(255) DEFAULT NULL,
  `genderEnum` varchar(255) DEFAULT NULL,
  `gmEnum` varchar(255) DEFAULT NULL,
  `health` bigint NOT NULL,
  `jobEnum` varchar(255) DEFAULT NULL,
  `mana` bigint NOT NULL,
  `maxHealth` bigint NOT NULL,
  `maxMana` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `reports` bit(1) NOT NULL,
  `townId` int NOT NULL,
  `worldId` int NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  `z` int NOT NULL,
  `user` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6i92nqh2ossxlu69airfs29ro` (`user`),
  CONSTRAINT `FK6i92nqh2ossxlu69airfs29ro` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela hot.player: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` (`id`, `capacity`, `chaseEnum`, `directionEnum`, `fightEnum`, `genderEnum`, `gmEnum`, `health`, `jobEnum`, `mana`, `maxHealth`, `maxMana`, `name`, `reports`, `townId`, `worldId`, `x`, `y`, `z`, `user`) VALUES
	(1, 13000, 'STAND', 'NORTH', 'BALANCED', 'MALE', 'ADM', 100, 'GOD', 50, 50, 0, 'Teste', b'0', 0, 1, 1025, 1025, 7, 1);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;

-- Copiando estrutura para tabela hot.player_ability
CREATE TABLE IF NOT EXISTS `player_ability` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `abilityEnum` varchar(255) DEFAULT NULL,
  `exp` double NOT NULL,
  `level` int NOT NULL,
  `player` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKruygbmvuq9pgl8ke4k26c0by7` (`player`),
  CONSTRAINT `FKruygbmvuq9pgl8ke4k26c0by7` FOREIGN KEY (`player`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela hot.player_ability: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `player_ability` DISABLE KEYS */;
INSERT INTO `player_ability` (`id`, `abilityEnum`, `exp`, `level`, `player`) VALUES
	(1, 'CLUB', 0, 1, 1),
	(2, 'FISHING', 0, 1, 1),
	(3, 'DISTANCE', 0, 1, 1),
	(4, 'LEVEL', 0, 1, 1),
	(5, 'FIST', 0, 1, 1),
	(6, 'SHILDING', 0, 1, 1),
	(7, 'SWORD', 0, 1, 1),
	(8, 'MAGIC', 0, 1, 1),
	(9, 'AXE', 0, 1, 1);
/*!40000 ALTER TABLE `player_ability` ENABLE KEYS */;

-- Copiando estrutura para tabela hot.player_look
CREATE TABLE IF NOT EXISTS `player_look` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lookAdddons` int NOT NULL,
  `lookBody` int NOT NULL,
  `lookFeet` int NOT NULL,
  `lookHead` int NOT NULL,
  `lookLegs` int NOT NULL,
  `lookType` int NOT NULL,
  `player` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32gkpp91ehc49q90i3caix05d` (`player`),
  CONSTRAINT `FK32gkpp91ehc49q90i3caix05d` FOREIGN KEY (`player`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela hot.player_look: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `player_look` DISABLE KEYS */;
INSERT INTO `player_look` (`id`, `lookAdddons`, `lookBody`, `lookFeet`, `lookHead`, `lookLegs`, `lookType`, `player`) VALUES
	(1, 2, 0, 0, 0, 0, 95, 1);
/*!40000 ALTER TABLE `player_look` ENABLE KEYS */;

-- Copiando estrutura para tabela hot.player_property
CREATE TABLE IF NOT EXISTS `player_property` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `propertyEnum` varchar(255) DEFAULT NULL,
  `value` bigint NOT NULL,
  `player` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKno4psgpstgmwriidfx1m2ys9x` (`player`),
  CONSTRAINT `FKno4psgpstgmwriidfx1m2ys9x` FOREIGN KEY (`player`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela hot.player_property: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `player_property` DISABLE KEYS */;
INSERT INTO `player_property` (`id`, `propertyEnum`, `value`, `player`) VALUES
	(1, 'SOUL', 0, 1),
	(2, 'SPEED', 0, 1),
	(3, 'LIGHT_BRIGHTNESS', 0, 1),
	(4, 'LIGHT_COLOR', 0, 1),
	(5, 'STAMINA', 0, 1);
/*!40000 ALTER TABLE `player_property` ENABLE KEYS */;

-- Copiando estrutura para tabela hot.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `banned` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `langue` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `premiumDays` int NOT NULL,
  `tempBan` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela hot.user: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `banned`, `birthday`, `email`, `langue`, `name`, `password`, `premiumDays`, `tempBan`) VALUES
	(1, NULL, NULL, '1@1.com', 'BRA', '1', '$2a$10$ZMh76T24c4DxYP0ib7KEVupbwNhQq7nqVdC6OZkz0ix21kowyRSSi', 30, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Copiando estrutura para tabela hot.world
CREATE TABLE IF NOT EXISTS `world` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `port` int NOT NULL,
  `pvptype` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela hot.world: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `world` DISABLE KEYS */;
INSERT INTO `world` (`id`, `ip`, `location`, `name`, `port`, `pvptype`) VALUES
	(1, '127.0.0.1', 'BRA', 'Borapoa', 7172, 0);
/*!40000 ALTER TABLE `world` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
