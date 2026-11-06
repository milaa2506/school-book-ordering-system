/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 10.4.32-MariaDB : Database - knjige

---

*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`knjige` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `knjige`;

DROP TABLE IF EXISTS `StavkaPorudzbine`;
DROP TABLE IF EXISTS `Porudzbina`;
DROP TABLE IF EXISTS `Knjiga`;
DROP TABLE IF EXISTS `Kupac`;
DROP TABLE IF EXISTS `Kategorija`;
DROP TABLE IF EXISTS `KnjizarDip`;
DROP TABLE IF EXISTS `Diploma`;
DROP TABLE IF EXISTS `Knjizar`;

/* KNJIZAR */
CREATE TABLE `Knjizar` (
`KnjizarID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`Ime` VARCHAR(50) NOT NULL,
`Prezime` VARCHAR(50) NOT NULL,
`KorisnickoIme` VARCHAR(30) NOT NULL,
`Lozinka` VARCHAR(30) NOT NULL,
PRIMARY KEY (`KnjizarID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `Knjizar` VALUES
(1,'Mila','Matic','mila','mila'),
(2,'Nikola','Djordjevic','nikola','nikola'),
(3,'Ana','Lazic','ana','ana'),
(4,'Stefan','Popovic','stefan','stefan'),
(5,'Jovana','Ristic','jovana','jovana');

/* DIPLOMA */
CREATE TABLE `Diploma` (
`DiplomaID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`Naziv` VARCHAR(50) NOT NULL,
`Opis` VARCHAR(300) NOT NULL,
PRIMARY KEY (`DiplomaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `Diploma` VALUES
(1,'Srednja skola - trgovac','Diploma srednje strucne skole za zanimanje trgovac.'),
(2,'Bibliotekar','Diploma visoke skole za bibliotekarstvo i informatiku.'),
(3,'Profesor srpskog jezika','Diploma Filoloskog fakulteta, smer srpski jezik i knjizevnost.'),
(4,'Master menadzer u izdavastvu','Diploma master studija iz oblasti menadzmenta u izdavastvu.');

/* KNJIZAR DIP */
CREATE TABLE `KnjizarDip` (
`KnjizarID` BIGINT(10) UNSIGNED NOT NULL,
`DiplomaID` BIGINT(10) UNSIGNED NOT NULL,
`DatumDiplome` DATE NOT NULL,
`ProsecnaOcena` DECIMAL(4,2) NOT NULL,
PRIMARY KEY (`KnjizarID`,`DiplomaID`),
CONSTRAINT `fk_knjizar_id2` FOREIGN KEY (`KnjizarID`) REFERENCES `Knjizar` (`KnjizarID`),
CONSTRAINT `fk_diploma_id` FOREIGN KEY (`DiplomaID`) REFERENCES `Diploma` (`DiplomaID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `KnjizarDip` VALUES
(1,1,'2016-06-15',4.52),
(1,3,'2021-10-01',8.74),
(2,1,'2015-06-20',4.10),
(2,2,'2019-07-05',8.20),
(3,3,'2020-09-28',9.15),
(4,1,'2017-06-18',3.95),
(4,4,'2023-10-12',9.40),
(5,2,'2022-07-01',8.65);

/* KATEGORIJA */
-- Kupci su podeljeni u 3 kategorije i u odnosu na kategoriju im se odobrava popust:
-- 1 Pojedinacni kupac -> 5%, 2 Visedetna porodica -> 10%, 3 Skola -> 15%
CREATE TABLE `Kategorija` (
`KategorijaID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`Naziv` VARCHAR(50) NOT NULL,
PRIMARY KEY (`KategorijaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `Kategorija` VALUES
(1,'Pojedinacni kupac'),
(2,'Visedetna porodica'),
(3,'Skola');

/* KUPAC */
CREATE TABLE `Kupac` (
`KupacID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`Ime` VARCHAR(50) NOT NULL,
`Prezime` VARCHAR(50) NOT NULL,
`Email` VARCHAR(100) NOT NULL,
`Telefon` VARCHAR(30) NOT NULL,
`KategorijaID` BIGINT(10) UNSIGNED NOT NULL,
PRIMARY KEY (`KupacID`),
CONSTRAINT `fk_kategorija_id` FOREIGN KEY (`KategorijaID`) REFERENCES `Kategorija` (`KategorijaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `Kupac` VALUES
(1,'Marija','Petrovic','marijapetrovic@gmail.com','0601112223',1),
(2,'Dragan','Nikolic','dragannikolic@gmail.com','0612223334',2),
(3,'Snezana','Ilic','os.vukkaradzic@gmail.com','0623334445',3),
(4,'Ivan','Jovanovic','ivanjovanovic@gmail.com','0634445556',1),
(5,'Jelena','Markovic','jelenamarkovic@gmail.com','0645556667',2),
(6,'Milan','Stojanovic','os.desankamaksimovic@gmail.com','0656667778',3),
(7,'Tamara','Pavlovic','tamarapavlovic@gmail.com','0667778889',1);

/* KNJIGA */
CREATE TABLE `Knjiga` (
`KnjigaID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`Naziv` VARCHAR(100) NOT NULL,
`Proizvodjac` VARCHAR(50) NOT NULL,
`Opis` VARCHAR(300) NOT NULL,
`Cena` DECIMAL(10,2) NOT NULL,
PRIMARY KEY (`KnjigaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `Knjiga` VALUES
(1,'Bukvar za 1. razred','Zavod za udzbenike','Udzbenik za pocetno citanje i pisanje sa ilustracijama.',780.00),
(2,'Matematika 1 - radni udzbenik','Klett','Radni udzbenik sa zadacima za prvi razred osnovne skole.',950.00),
(3,'Srpski jezik 3 - citanka','Novi Logos','Citanka sa odabranim tekstovima za treci razred.',890.00),
(4,'Priroda i drustvo 4','Eduka','Udzbenik sa ogledima i pitanjima za proveru znanja.',920.00),
(5,'Engleski jezik 2 - udzbenik','Data Status','Udzbenik sa audio materijalom za drugi razred.',1450.00),
(6,'Pinokio - lektira','Vulkan izdavastvo','Lektira za drugi razred, ilustrovano izdanje.',650.00),
(7,'Hajduk Stanko - lektira','Laguna','Lektira za sedmi razred, roman Janka Veselinovica.',720.00),
(8,'Geografski atlas','Kreativni centar','Atlas za peti do osmi razred osnovne skole.',1100.00),
(9,'Zbirka zadataka iz matematike 5','Gerundijum','Zbirka resenih i neresenih zadataka za peti razred.',850.00),
(10,'Fizika 6 - udzbenik','BIGZ skolstvo','Udzbenik fizike za sesti razred sa laboratorijskim vezbama.',990.00);

/* PORUDZBINA */
-- UkupanIznos = IznosBezPopusta - IznosBezPopusta * Popust / 100
-- Popust (u procentima) zavisi od kategorije kupca
CREATE TABLE `Porudzbina` (
`PorudzbinaID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`DatumVreme` DATETIME NOT NULL,
`DatumVremeIsporuke` DATETIME NOT NULL,
`IznosBezPopusta` DECIMAL(10,2) NOT NULL,
`Popust` DECIMAL(5,2) NOT NULL,
`UkupanIznos` DECIMAL(10,2) NOT NULL,
`KupacID` BIGINT(10) UNSIGNED NOT NULL,
`KnjizarID` BIGINT(10) UNSIGNED NOT NULL,
PRIMARY KEY (`PorudzbinaID`),
CONSTRAINT `fk_kupac_id` FOREIGN KEY (`KupacID`) REFERENCES `Kupac` (`KupacID`),
CONSTRAINT `fk_knjizar_id` FOREIGN KEY (`KnjizarID`) REFERENCES `Knjizar` (`KnjizarID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `Porudzbina` VALUES
-- Kupac 1 (Pojedinacni kupac, 5%): 1730.00 - 5% = 1643.50
(1,'2026-09-01 10:15:00','2026-09-03 12:00:00',1730.00,5.00,1643.50,1,1),
-- Kupac 2 (Visedetna porodica, 10%): 5430.00 - 10% = 4887.00
(2,'2026-09-02 11:30:00','2026-09-05 13:00:00',5430.00,10.00,4887.00,2,2),
-- Kupac 3 (Skola, 15%): 43250.00 - 15% = 36762.50
(3,'2026-09-05 09:00:00','2026-09-10 10:00:00',43250.00,15.00,36762.50,3,3),
-- Kupac 4 (Pojedinacni kupac, 5%): 2100.00 - 5% = 1995.00
(4,'2026-09-10 14:20:00','2026-09-12 16:00:00',2100.00,5.00,1995.00,4,1),
-- Kupac 5 (Visedetna porodica, 10%): 4620.00 - 10% = 4158.00
(5,'2026-09-13 17:10:00','2026-09-17 12:00:00',4620.00,10.00,4158.00,5,4),
-- Kupac 6 (Skola, 15%): 55200.00 - 15% = 46920.00
(6,'2026-09-14 12:00:00','2026-09-21 09:00:00',55200.00,15.00,46920.00,6,5),
-- Kupac 7 (Pojedinacni kupac, 5%): 3110.00 - 5% = 2954.50
(7,'2026-09-15 09:45:00','2026-09-18 14:00:00',3110.00,5.00,2954.50,7,2);

/* STAVKA PORUDZBINE */
CREATE TABLE `StavkaPorudzbine` (
`PorudzbinaID` BIGINT(10) UNSIGNED NOT NULL,
`Rb` INT(7) NOT NULL,
`Kolicina` INT(7) NOT NULL,
`Cena` DECIMAL(10,2) NOT NULL,
`Iznos` DECIMAL(10,2) NOT NULL,
`KnjigaID` BIGINT(10) UNSIGNED NOT NULL,
PRIMARY KEY (`PorudzbinaID`,`Rb`),
CONSTRAINT `fk_porudzbina_id` FOREIGN KEY (`PorudzbinaID`) REFERENCES `Porudzbina` (`PorudzbinaID`) ON DELETE CASCADE,
CONSTRAINT `fk_knjiga_id` FOREIGN KEY (`KnjigaID`) REFERENCES `Knjiga` (`KnjigaID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `StavkaPorudzbine` VALUES
-- Porudzbina 1: 1 x 780 + 1 x 950 = 1730
(1,1,1,780.00,780.00,1),
(1,2,1,950.00,950.00,2),

-- Porudzbina 2: 3 x 890 + 3 x 920 = 5430
(2,1,3,890.00,2670.00,3),
(2,2,3,920.00,2760.00,4),

-- Porudzbina 3: 25 x 780 + 25 x 950 = 43250
(3,1,25,780.00,19500.00,1),
(3,2,25,950.00,23750.00,2),

-- Porudzbina 4: 1 x 650 + 1 x 1450 = 2100
(4,1,1,650.00,650.00,6),
(4,2,1,1450.00,1450.00,5),

-- Porudzbina 5: 2 x 1100 + 1 x 720 + 2 x 850 = 4620
(5,1,2,1100.00,2200.00,8),
(5,2,1,720.00,720.00,7),
(5,3,2,850.00,1700.00,9),

-- Porudzbina 6: 30 x 990 + 30 x 850 = 55200
(6,1,30,990.00,29700.00,10),
(6,2,30,850.00,25500.00,9),

-- Porudzbina 7: 1 x 890 + 2 x 650 + 1 x 920 = 3110
(7,1,1,890.00,890.00,3),
(7,2,2,650.00,1300.00,6),
(7,3,1,920.00,920.00,4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40111 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
