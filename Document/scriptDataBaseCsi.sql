-- MySQL Workbench Forward Engineering


-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema fil_rouge_3
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fil_rouge_3
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fil_rouge_3` DEFAULT CHARACTER SET utf8 ;
USE `fil_rouge_3` ;

-- -----------------------------------------------------
-- Table `fil_rouge_3`.`archive_enquete`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fil_rouge_3`.`archive_enquete` (
  `nom_enquete_archive` VARCHAR(45) NULL DEFAULT NULL,
  `type_affaire_archive` VARCHAR(45) NULL DEFAULT NULL,
  `date_creation_archive` DATE NULL DEFAULT NULL,
  `localisation_archive` VARCHAR(45) NULL DEFAULT NULL,
  `statut_archive` VARCHAR(45) NULL DEFAULT NULL,
  `classee_archive` TINYINT(4) NULL DEFAULT NULL,
  `enquete_id_archive` INT(11) NOT NULL,
  PRIMARY KEY (`enquete_id_archive`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fil_rouge_3`.`archive_personne_impliquee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fil_rouge_3`.`archive_personne_impliquee` (
  `id_archive_personne_impliquee` INT(11) NOT NULL,
  `nom` VARCHAR(45) NULL DEFAULT NULL,
  `prenom` VARCHAR(45) NULL DEFAULT NULL,
  `date_naissance` DATE NULL DEFAULT NULL,
  `genre` VARCHAR(45) NULL DEFAULT NULL,
  `photo` VARCHAR(1000) NULL DEFAULT NULL,
  `date_deces` DATE NULL DEFAULT NULL,
  `adresse` MEDIUMTEXT NULL DEFAULT NULL,
  `grade` VARCHAR(45) NULL DEFAULT NULL,
  `competences` VARCHAR(45) NULL DEFAULT NULL,
  `date_prise_service` DATE NULL DEFAULT NULL,
  `actif` VARCHAR(45) NULL DEFAULT NULL,
  `telephone` VARCHAR(45) NULL DEFAULT NULL,
  `taille` FLOAT NULL DEFAULT NULL,
  `poids` INT(11) NULL DEFAULT NULL,
  `signe_distinctif` VARCHAR(45) NULL DEFAULT NULL,
  `empreinte` VARCHAR(45) NULL DEFAULT NULL,
  `casier` TINYINT(4) NULL DEFAULT NULL,
  `nombre_condamnation` INT(11) NULL DEFAULT NULL,
  `type_condamnation` VARCHAR(45) NULL DEFAULT NULL,
  `nationalite` VARCHAR(45) NULL DEFAULT NULL,
  `statut` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id_archive_personne_impliquee`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fil_rouge_3`.`enquete`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fil_rouge_3`.`enquete` (
  `id_enquete` INT(11) NOT NULL AUTO_INCREMENT,
  `nom_enquete` VARCHAR(45) NULL DEFAULT NULL,
  `type_affaire` VARCHAR(45) NULL DEFAULT NULL,
  `date_creation` DATE NULL DEFAULT NULL,
  `localisation` VARCHAR(45) NULL DEFAULT NULL,
  `statut` VARCHAR(45) NULL DEFAULT NULL,
  `classee` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id_enquete`),
  UNIQUE INDEX `uk_enquete` (`nom_enquete` ASC, `date_creation` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fil_rouge_3`.`humain`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fil_rouge_3`.`humain` (
  `id_humain` INT(11) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(45) NULL DEFAULT NULL,
  `prenom` VARCHAR(45) NULL DEFAULT NULL,
  `date_naissance` DATE NULL DEFAULT NULL,
  `genre` VARCHAR(45) NULL DEFAULT NULL,
  `photo` VARCHAR(1000) NULL DEFAULT NULL,
  `date_deces` DATE NULL DEFAULT NULL,
  `adresse` MEDIUMTEXT NULL DEFAULT NULL,
  `grade` VARCHAR(45) NULL DEFAULT NULL,
  `competences` VARCHAR(45) NULL DEFAULT NULL,
  `date_prise_service` DATE NULL DEFAULT NULL,
  `actif` VARCHAR(45) NULL DEFAULT NULL,
  `telephone` VARCHAR(45) NULL DEFAULT NULL,
  `taille` FLOAT NULL DEFAULT NULL,
  `poids` INT(11) NULL DEFAULT NULL,
  `signe_distinctif` VARCHAR(45) NULL DEFAULT NULL,
  `empreinte` VARCHAR(1000) NULL DEFAULT NULL,
  `casier` TINYINT(4) NULL DEFAULT NULL,
  `nombre_condamnation` INT(11) NULL DEFAULT NULL,
  `type_condamnation` VARCHAR(45) NULL DEFAULT NULL,
  `nationalite` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id_humain`),
  UNIQUE INDEX `uk_humain` (`nom` ASC, `prenom` ASC, `date_naissance` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fil_rouge_3`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fil_rouge_3`.`status` (
  `id` INT(11) NOT NULL,
  `status` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `status_UNIQUE` (`status` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fil_rouge_3`.`personne_impliquee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fil_rouge_3`.`personne_impliquee` (
  `id_personne_implique` INT(11) NOT NULL AUTO_INCREMENT,
  `humain_id` INT(11) NOT NULL,
  `enquete_id` INT(11) NOT NULL,
  `status_id` INT(11) NOT NULL,
  PRIMARY KEY (`id_personne_implique`),
  UNIQUE INDEX `uk_impliquee` (`humain_id` ASC, `enquete_id` ASC, `status_id` ASC),
  INDEX `fk_personne_impliquee_enquete1_idx` (`enquete_id` ASC),
  INDEX `fk_personne_impliquee_status1_idx` (`status_id` ASC),
  INDEX `fk_personne_impliquee_Humain1` (`humain_id` ASC),
  CONSTRAINT `fk_personne_impliquee_Humain1`
    FOREIGN KEY (`humain_id`)
    REFERENCES `fil_rouge_3`.`humain` (`id_humain`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_personne_impliquee_enquete1`
    FOREIGN KEY (`enquete_id`)
    REFERENCES `fil_rouge_3`.`enquete` (`id_enquete`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_personne_impliquee_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `fil_rouge_3`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


