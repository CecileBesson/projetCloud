-- Cloud project --
-- production database sql script --

CREATE TABLE IF NOT EXISTS `position`
(
    `id_position` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `lat`         DOUBLE          NOT NULL,
    `lon`         DOUBLE          NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `user`
(
    `id`          INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `first_name`  VARCHAR(100)    NOT NULL,
    `last_name`   VARCHAR(100)    NOT NULL,
    `birth_day`   DATE            NOT NULL,
    `fk_position` INT             NOT NULL,
    CONSTRAINT `fkPositionConstraint` FOREIGN KEY (`fk_position`) REFERENCES `position` (`id_position`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;