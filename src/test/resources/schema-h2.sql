-- Projet Cloud --

CREATE SCHEMA IF NOT EXISTS `cloud_equipe_e`;
USE `cloud_equipe_e`;

CREATE TABLE `position`(
`id_position` int auto_increment primary key,
`lat` DOUBLE  NOT NULL,
`lon` DOUBLE  NOT NULL
);


CREATE TABLE `user`(
`id` int auto_increment primary key,
`first_name` VARCHAR(100) NOT NULL,
`last_name` VARCHAR(100) NOT NULL,
`birth_day` DATE NOT NULL,
`fk_position` INT NOT NULL,
CONSTRAINT `fkPositionConstraint` FOREIGN KEY(`fk_position`) REFERENCES `position`(`id_position`) ON DELETE CASCADE ON UPDATE CASCADE
);