-- MySQL Script generated by MySQL Workbench
-- dom 16 feb 2020 00:30:46 CST
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema EPS_SYSTEM
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `EPS_SYSTEM` ;

-- -----------------------------------------------------
-- Schema EPS_SYSTEM
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `EPS_SYSTEM` ;
USE `EPS_SYSTEM` ;

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`ROL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`ROL` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`ROL` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`USER` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`USER` (
  `userId` VARCHAR(45) NOT NULL,
  `DPI` VARCHAR(45) NOT NULL,
  `codePersonal` VARCHAR(45) NULL,
  `academicRegister` VARCHAR(45) NULL,
  `firstName` VARCHAR(100) NOT NULL,
  `lastName` VARCHAR(100) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone1` VARCHAR(45) NOT NULL,
  `phone2` VARCHAR(45) NULL,
  `password` VARCHAR(400) NOT NULL,
  `ROL_id` INT NOT NULL,
  `direction` VARCHAR(100) NULL,
  `status` TINYINT NOT NULL,
  `removable` TINYINT NOT NULL DEFAULT 0,
  `eps_committee` TINYINT(1) NOT NULL DEFAULT 0,
  `name_company_work` VARCHAR(100) NULL,
  `phone_company_work` VARCHAR(45) NULL,
  `direction_company_work` VARCHAR(100),
  `personal_resume` MEDIUMBLOB NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `fk_USER_ROL1`
    FOREIGN KEY (`ROL_id`)
    REFERENCES `EPS_SYSTEM`.`ROL` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `DPI_UNIQUE` ON `EPS_SYSTEM`.`USER` (`DPI` ASC) ;

CREATE INDEX `fk_USER_ROL1_idx` ON `EPS_SYSTEM`.`USER` (`ROL_id` ASC) ;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`CAREER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`CAREER` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`CAREER` (
  `codigo` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`USER_CAREER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`USER_CAREER` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`USER_CAREER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `USER_userId` VARCHAR(45) NOT NULL,
  `CAREER_codigo` INT NOT NULL,
  `PROCESS_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_USER_CAREER_USER1`
    FOREIGN KEY (`USER_userId`)
    REFERENCES `EPS_SYSTEM`.`USER` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_USER_CAREER_PROCESS1`
      FOREIGN KEY (`PROCESS_id`)
      REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_CAREER_CAREER1`
    FOREIGN KEY (`CAREER_codigo`)
    REFERENCES `EPS_SYSTEM`.`CAREER` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_USER_CAREER_USER1_idx` ON `EPS_SYSTEM`.`USER_CAREER` (`USER_userId` ASC) ;

CREATE INDEX `fk_USER_CAREER_PROCESS1_idx` ON `EPS_SYSTEM`.`USER_CAREER` (`PROCESS_id` ASC) ;

CREATE INDEX `fk_USER_CAREER_CAREER1_idx` ON `EPS_SYSTEM`.`USER_CAREER` (`CAREER_codigo` ASC) ;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`TAIL_COORDINATOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`TAIL_COORDINATOR` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `USER_CAREER_id` INT(11) NOT NULL,
  `PROCESS_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_TAIL_COORDINATOR_USER_CAREER1`
    FOREIGN KEY (`USER_CAREER_id`)
    REFERENCES `EPS_SYSTEM`.`USER_CAREER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TAIL_COORDINATOR_PROCESS1`
    FOREIGN KEY (`PROCESS_id`)
    REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_TAIL_COORDINATOR_USER_CAREER1_idx` ON `EPS_SYSTEM`.`TAIL_COORDINATOR` (`USER_CAREER_id` ASC);

CREATE INDEX `fk_TAIL_COORDINATOR_PROCESS1_idx` ON `EPS_SYSTEM`.`TAIL_COORDINATOR` (`PROCESS_id` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`TAIL_COMMITTEE_EPS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`TAIL_COMMITTEE_EPS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dateRequestReview` DATE NOT NULL,
  `PROCESS_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_TAIL_COMMITTEE_EPS_PROCESS1`
    FOREIGN KEY (`PROCESS_id`)
    REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_TAIL_COMMITTEE_EPS_PROCESS1_idx` ON `EPS_SYSTEM`.`TAIL_COMMITTEE_EPS` (`PROCESS_id` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`BIBLIOGRAPHY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`BIBLIOGRAPHY` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`BIBLIOGRAPHY` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(100) NOT NULL,
  `publicactionYear` DATE NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `editorial` VARCHAR(45) NOT NULL,
  `position` INT NOT NULL,
  `PROJECT_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_BIBLIOGRAPHY_PROJECT1`
    FOREIGN KEY (`PROJECT_id`)
    REFERENCES `EPS_SYSTEM`.`PROJECT` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_BIBLIOGRAPHY_PROJECT1_idx` ON `EPS_SYSTEM`.`BIBLIOGRAPHY` (`PROJECT_id` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`PROJECT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`PROJECT` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`PROJECT` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(400) NOT NULL,
  `status` TINYINT NOT NULL,
  `schedule` MEDIUMBLOB NOT NULL,
  `investmentPlan` MEDIUMBLOB NOT NULL,
  `annexed` MEDIUMBLOB NULL DEFAULT NULL,
  `limitReceptionDate` DATE NOT NULL,
  `PROCESS_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_PROJECT_PROCESS1`
    FOREIGN KEY (`PROCESS_id`)
    REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `fk_PROJECT_PROCESS1_idx` ON `EPS_SYSTEM`.`PROJECT` (`PROCESS_id` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`REQUERIMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`REQUERIMENT` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`REQUERIMENT` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `writtenRequest` MEDIUMBLOB NOT NULL,
  `inscriptionConstancy` MEDIUMBLOB NOT NULL,
  `pensumClosure` MEDIUMBLOB NOT NULL,
  `propedeuticConstancy` MEDIUMBLOB NOT NULL,
  `EPSpreproject` MEDIUMBLOB NOT NULL,
  `AEIOsettlement` MEDIUMBLOB NULL,
  `PROCESS_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_REQUERIMENT_PROCESS1`
    FOREIGN KEY (`PROCESS_id`)
    REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_REQUERIMENT_PROCESS1_idx` ON `EPS_SYSTEM`.`REQUERIMENT` (`PROCESS_id` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`APPOINTMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`APPOINTMENT` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`APPOINTMENT` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `USER_adviser` VARCHAR(45) NULL,
  `USER_reviewer` VARCHAR(45) NULL,
  `adviser_state` ENUM('APPROVED', 'CHANGE', 'REVIEW','ELECTION') NULL,
  `reviewer_state` ENUM('APPROVED', 'CHANGE', 'REVIEW','ELECTION') NULL,
  `date_action` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_APPOINTMENT_USER1`
    FOREIGN KEY (`USER_adviser`)
    REFERENCES `EPS_SYSTEM`.`USER` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_APPOINTMENT_USER2`
    FOREIGN KEY (`USER_reviewer`)
    REFERENCES `EPS_SYSTEM`.`USER` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_APPOINTMENT_USER1_idx` ON `EPS_SYSTEM`.`APPOINTMENT` (`USER_adviser` ASC) ;

CREATE INDEX `fk_APPOINTMENT_USER2_idx` ON `EPS_SYSTEM`.`APPOINTMENT` (`USER_reviewer` ASC) ;

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`DELIVER__EPS_COMPLETION_DOCUMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`DELIVER__EPS_COMPLETION_DOCUMENT` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`DELIVER__EPS_COMPLETION_DOCUMENT` (
  `id` INT NOT NULL,
  `deliveryFinalReport` TINYINT NOT NULL,
  `counterpartSettlement` MEDIUMBLOB NOT NULL,
  `reviewerLetter` MEDIUMBLOB NOT NULL,
  `adviserLetter` MEDIUMBLOB NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`PROCESS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`PROCESS` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`PROCESS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state` ENUM('ACTIVO', 'INACTIVA', 'REVISION', 'RECHAZADO') NOT NULL,
  `approvedCareerCoordinator` TINYINT NULL,
  `documentApprovedCareerCoordinator` MEDIUMBLOB NULL,
  `approvalEPSCommission` TINYINT NULL,
  `APPOINTMENT_id` INT NULL,
  `approvedEPSDevelopment` TINYINT NOT NULL,
  `date_ApproveddEpsDevelopment` DATE NULL,
  `DELIVER__EPS_COMPLETION_DOCUMENT_id` INT NULL,
  `progress` INT(100) NULL,
  `SUPERVISOR_EPS_userId` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_PROCESS_APPOINTMENT1`
    FOREIGN KEY (`APPOINTMENT_id`)
    REFERENCES `EPS_SYSTEM`.`APPOINTMENT` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PROCESS_DELIVER__EPS_COMPLETION_DOCUMENT1`
    FOREIGN KEY (`DELIVER__EPS_COMPLETION_DOCUMENT_id`)
    REFERENCES `EPS_SYSTEM`.`DELIVER__EPS_COMPLETION_DOCUMENT` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PROCESS_USER1`
    FOREIGN KEY (`SUPERVISOR_EPS_userId`)
    REFERENCES `EPS_SYSTEM`.`USER` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_PROCESS_APPOINTMENT1_idx` ON `EPS_SYSTEM`.`PROCESS` (`APPOINTMENT_id` ASC);

CREATE INDEX `fk_PROCESS_DELIVER__EPS_COMPLETION_DOCUMENT1_idx` ON `EPS_SYSTEM`.`PROCESS` (`DELIVER__EPS_COMPLETION_DOCUMENT_id` ASC);

CREATE INDEX `fk_PROCESS_USER1_idx` ON `EPS_SYSTEM`.`PROCESS` (`SUPERVISOR_EPS_userId` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`JOURNAL_LOG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`JOURNAL_LOG` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `activity` VARCHAR(45) NOT NULL,
  `dateTime` DATETIME NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `PROCESS_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_JOURNAL_LOG_PROCESS1`
    FOREIGN KEY (`PROCESS_id`)
    REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_JOURNAL_LOG_PROCESS1_idx` ON `EPS_SYSTEM`.`JOURNAL_LOG` (`PROCESS_id` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`IMAGE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`IMAGE` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `image` MEDIUMBLOB NOT NULL,
  `JOURNAL_LOG_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_IMAGE_JOURNAL_LOG1`
    FOREIGN KEY (`JOURNAL_LOG_id`)
    REFERENCES `EPS_SYSTEM`.`JOURNAL_LOG` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_IMAGE_JOURNAL_LOG1_idx` ON `EPS_SYSTEM`.`IMAGE` (`JOURNAL_LOG_id` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`LINK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`LINK` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `link` BLOB NOT NULL,
  `JOURNAL_LOG_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_LINK_JOURNAL_LOG1`
    FOREIGN KEY (`JOURNAL_LOG_id`)
    REFERENCES `EPS_SYSTEM`.`JOURNAL_LOG` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_LINK_JOURNAL_LOG1_idx` ON `EPS_SYSTEM`.`LINK` (`JOURNAL_LOG_id` ASC) ;

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`COMENTARY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`COMMENTARY` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` BLOB NOT NULL,
  `date` DATE NOT NULL,
  `JOURNAL_LOG` INT(11) NOT NULL,
  `userId` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_COMMENTARY_JOURNAL_LOG1`
    FOREIGN KEY (`JOURNAL_LOG`)
    REFERENCES `EPS_SYSTEM`.`JOURNAL_LOG` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_COMMENTARY_USER1`
    FOREIGN KEY (`userId`)
    REFERENCES `EPS_SYSTEM`.`USER` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_COMMENTARY_JOURNAL_LOG1_idx` ON `EPS_SYSTEM`.`COMMENTARY` (`JOURNAL_LOG` ASC) ;

CREATE INDEX `fk_COMMENTARY_USER1_idx` ON `EPS_SYSTEM`.`COMMENTARY` (`userId` ASC) ;

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`SUPERVISION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`SUPERVISION` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`SUPERVISION` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `annotation` VARCHAR(200) NULL,
  `PROCESS_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_SUPERVISION_PROCESS1`
    FOREIGN KEY (`PROCESS_id`)
    REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_SUPERVISION_PROCESS1_idx` ON `EPS_SYSTEM`.`SUPERVISION` (`PROCESS_id` ASC) ;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`DOCUMENT_FINAL_EPS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`DOCUMENT_FINAL_EPS` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`DOCUMENT_FINAL_EPS` (
  `id` INT NOT NULL,
  `epsCareerSupervisorOpinion` VARCHAR(45) NOT NULL,
  `certificateConclusionByEPSCoordinator` MEDIUMBLOB NOT NULL,
  `finalReport` MEDIUMBLOB NOT NULL,
  `transcriptionCertificateConclusionEPS` MEDIUMBLOB NOT NULL,
  `transferToCoordinatingSecretary` TINYINT NOT NULL,
  `PROCESS_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_DOCUMENT_FINAL_EPS_PROCESS1`
    FOREIGN KEY (`PROCESS_id`)
    REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_DOCUMENT_FINAL_EPS_PROCESS1_idx` ON `EPS_SYSTEM`.`DOCUMENT_FINAL_EPS` (`PROCESS_id` ASC) ;

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`TITLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`TITLE` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`TITLE` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(150) NOT NULL,
  `position` INT NOT NULL,
  `TITLE_parent_id` INT NULL,
  `SECTION_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_TITULO_TITULO1`
    FOREIGN KEY (`TITLE_parent_id`)
    REFERENCES `EPS_SYSTEM`.`TITLE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TITLE_SECTION1`
    FOREIGN KEY (`SECTION_id`)
    REFERENCES `EPS_SYSTEM`.`SECTION` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_TITULO_TITULO1_idx` ON `EPS_SYSTEM`.`TITLE` (`TITLE_parent_id` ASC) ;

CREATE INDEX `fk_TITLE_SECTION1_idx` ON `EPS_SYSTEM`.`TITLE` (`SECTION_id` ASC) ;

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`TEXTO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`TEXTO` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`TEXTO` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` BLOB NOT NULL,
  `TITLE_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_TEXTO_TITULO1`
    FOREIGN KEY (`TITLE_id`)
    REFERENCES `EPS_SYSTEM`.`TITLE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_TEXTO_TITULO1_idx` ON `EPS_SYSTEM`.`TEXTO` (`TITLE_id` ASC) ;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`SECTION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`SECTION` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`SECTION` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `PROJECT_id` INT NOT NULL,
  `type` TINYINT NOT NULL,
  `lastModificationDate` DATE NOT NULL,
  `position` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_SECTION_PROJECT1`
    FOREIGN KEY (`PROJECT_id`)
    REFERENCES `EPS_SYSTEM`.`PROJECT` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_SECTION_PROJECT1_idx` ON `EPS_SYSTEM`.`SECTION` (`PROJECT_id` ASC);

-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`DECIMAL_COORDINATE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`DECIMAL_COORDINATE` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`DECIMAL_COORDINATE` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `position` INT NOT NULL,
  `PROJECT_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_COORDINATE_PROJECT1`
    FOREIGN KEY (`PROJECT_id`)
    REFERENCES `EPS_SYSTEM`.`PROJECT` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_COORDINATE_PROJECT1_idx` ON `EPS_SYSTEM`.`DECIMAL_COORDINATE` (`PROJECT_id` ASC) ;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`OBJECTIVES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`OBJECTIVES` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`OBJECTIVES` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` TINYINT NOT NULL,
  `text` VARCHAR(300) NOT NULL,
  `lastModificationDate` DATE NOT NULL,
  `position` INT NOT NULL,
  `PROJECT_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_OBJECTIVES_PROJECT1`
    FOREIGN KEY (`PROJECT_id`)
    REFERENCES `EPS_SYSTEM`.`PROJECT` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_OBJECTIVES_PROJECT1_idx` ON `EPS_SYSTEM`.`OBJECTIVES` (`PROJECT_id` ASC) ;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`CORRECTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`CORRECTION` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` BLOB NOT NULL,
  `date` VARCHAR(45) NOT NULL,
  `status` TINYINT NULL,
  `type` ENUM('BIBLIOGRAPHY', 'OBJETIVES', 'SPECIFIC_OBJETIVES', 'CALENDAR', 'PLAN', 'ANEXO', 'TITLE', 'COORDINATE', 'OTHER','REJECTED','ACCEPTED') NOT NULL,
  `USER_userId` VARCHAR(45) NOT NULL,
  `SECTION_id` INT NULL,
  `PROJECT_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_table1_USER1`
    FOREIGN KEY (`USER_userId`)
    REFERENCES `EPS_SYSTEM`.`USER` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CORRECTION_SECTION1`
    FOREIGN KEY (`SECTION_id`)
    REFERENCES `EPS_SYSTEM`.`SECTION` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CORRECTION_PROJECT1`
    FOREIGN KEY (`PROJECT_id`)
    REFERENCES `EPS_SYSTEM`.`PROJECT` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_table1_USER1_idx` ON `EPS_SYSTEM`.`CORRECTION` (`USER_userId` ASC) ;

CREATE INDEX `fk_CORRECTION_SECTION1_idx` ON `EPS_SYSTEM`.`CORRECTION` (`SECTION_id` ASC) ;

CREATE INDEX `fk_CORRECTION_PROJECT1_idx` ON `EPS_SYSTEM`.`CORRECTION` (`PROJECT_id` ASC) ;



-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`PROPERTY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`PROPERTY` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`PROPERTY` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `valueInt` INT NULL,
  `valueDate` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EPS_SYSTEM`.`NOTIFICATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EPS_SYSTEM`.`NOTIFICATION` ;

CREATE TABLE IF NOT EXISTS `EPS_SYSTEM`.`NOTIFICATION` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `description` VARCHAR(300) NOT NULL,
  `review` TINYINT NOT NULL,
  `shippingDate` DATE NOT NULL,
  `PROCESS_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_NOTIFICATION_PROCESS1`
    FOREIGN KEY (`PROCESS_id`)
    REFERENCES `EPS_SYSTEM`.`PROCESS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_NOTIFICATION_PROCESS1_idx` ON `EPS_SYSTEM`.`NOTIFICATION` (`PROCESS_id` ASC) ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO PROPERTY(name, valueInt) VALUES ("MAXIMUM_SUPERVISOR_WORKLOAD",10);
INSERT INTO PROPERTY(name, valueInt) VALUES ("VALIDATION_PERCENTAGE_EXTENSION",70);
INSERT INTO PROPERTY(name, valueInt) VALUES ("REVIEW_TIME_DAYS",8);
INSERT INTO PROPERTY(name, valueInt) VALUES ("TIME_OF_PROCESS_WITHOUT_MOVEMENT",2);
INSERT INTO PROPERTY(name, valueDate) VALUES ("DEADLINE_TO_SUBMIT_DOCUMENT",CURDATE());
INSERT INTO PROPERTY(name, valueInt) VALUES ("MINIMUN_EXECUTION_MONTHS",6);
INSERT INTO PROPERTY(name, valueInt) VALUES ("MAXIMUN_EXECUTION_MONTHS",12);
INSERT INTO PROPERTY(name, valueInt) VALUES ("CHARACTER_LIMIT_TITLE",300);
INSERT INTO PROPERTY(name, valueInt) VALUES ("LIMIT_GENERAL_OBJECTIVE",2);
INSERT INTO PROPERTY(name, valueInt) VALUES ("LIMIT_SPECIFIC_OBJECTIVE",8);
INSERT INTO PROPERTY(name, valueInt) VALUES ("CHARACTER_LIMIT_JUSTIFICATION",3500);
INSERT INTO PROPERTY(name, valueDate) VALUES ("GENERAL_LIMIT_RECEPTION_DATE",CURDATE());

INSERT INTO CAREER(codigo, name) VALUES (58, "Ingeniería en Ciencias y Sistemas");
INSERT INTO CAREER(codigo, name) VALUES (34, "Ingeniería Mecánica");
INSERT INTO CAREER(codigo, name) VALUES (33, "Ingeniería Civil");
INSERT INTO CAREER(codigo, name) VALUES (35, "Ingeniería Industrial");
INSERT INTO CAREER(codigo, name) VALUES (36, "Ingeniería Mecánica-Industrial");

INSERT INTO ROL(name) values("Dirección");
INSERT INTO ROL(name) values("Coordinador_EPS");
INSERT INTO ROL(name) values("Secretaria_EPS");
INSERT INTO ROL(name) values("Coordinador_Carrera");
INSERT INTO ROL(name) values("Secretaria_Coordinación");
INSERT INTO ROL(name) values("Estudiante");
INSERT INTO ROL(name) values("Revisor");
INSERT INTO ROL(name) values("Asesor");
INSERT INTO ROL(name) values("Supervisor_EPS");
