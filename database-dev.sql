-- Projet Cloud --
-- sql file for local databases (mysql) --
DROP DATABASE IF EXISTS `cloud_equipe_e`;
CREATE DATABASE `cloud_equipe_e` CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `cloud_equipe_e`;


CREATE TABLE IF NOT EXISTS `position`(
`id_position` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
`lat` DOUBLE  NOT NULL,
`lon` DOUBLE  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `user`(
`id` VARCHAR(100) PRIMARY KEY NOT NULL,
`first_name` VARCHAR(100) NOT NULL,
`last_name` VARCHAR(100) NOT NULL,
`birth_day` DATE NOT NULL,
`fk_position` INT NOT NULL,
CONSTRAINT `fkPositionConstraint` FOREIGN KEY(`fk_position`) REFERENCES `position`(`id_position`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;