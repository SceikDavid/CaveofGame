DROP DATABASE IF EXISTS TheCaveOfGame;
CREATE DATABASE TheCaveOfGame;
USE TheCaveOfGame;

-- 1. TABELLA UTENTE
CREATE TABLE `utente` (
                          `Username` varchar(25) NOT NULL,
                          `Nome` varchar(45) DEFAULT NULL,
                          `Cognome` varchar(45) DEFAULT NULL,
                          `Email` varchar(45) DEFAULT NULL,
                          `Telefono` varchar(15) DEFAULT NULL,
                          `Indirizzo` varchar(45) DEFAULT NULL,
                          `Password` varchar(45) DEFAULT NULL,
                          `admin` tinyint(1) DEFAULT NULL,
                          PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `utente` WRITE;
INSERT INTO `utente` VALUES ('admin','admin','admin','admin@gmail.com','3456789042','via 1','admin',1);
UNLOCK TABLES;

-- 2. TABELLA PRODOTTO (Padre - Aggiornata con Azienda, rimosso CodiceSKU)
CREATE TABLE `prodotto` (
                            `idProdotto` int NOT NULL AUTO_INCREMENT,
                            `nomeProdotto` varchar(100) DEFAULT NULL,
                            `descrizione` varchar(3000) DEFAULT NULL,
                            `quantita` int DEFAULT NULL,
                            `prezzo` DECIMAL(10, 2) NOT NULL,
                            `Azienda` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`idProdotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 3. TABELLE DI SPECIALIZZAZIONE DEI PRODOTTI (Figlie)
CREATE TABLE `console` (
                           `idProdotto` int NOT NULL,
                           `Modello` varchar(50),
                           `Colore` varchar(30),
                           `Memoria` varchar(20),
                           PRIMARY KEY (`idProdotto`),
                           CONSTRAINT `console_ibfk_1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `videogioco` (
                              `idProdotto` int NOT NULL,
                              `Pegi` int,
                              `Piattaforma` varchar(50),
                              PRIMARY KEY (`idProdotto`),
                              CONSTRAINT `videogioco_ibfk_1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gadget` (
                          `idProdotto` int NOT NULL,
                          `Tipologia` varchar(50),
                          PRIMARY KEY (`idProdotto`),
                          CONSTRAINT `gadget_ibfk_1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 4. TABELLA CARRELLO
CREATE TABLE `carrello` (
                            `idCarrello` int NOT NULL auto_increment,
                            `username` varchar(25) DEFAULT NULL,
                            `totale` double DEFAULT NULL,
                            PRIMARY KEY (`idCarrello`),
                            KEY `username` (`username`),
                            CONSTRAINT `carrello_ibfk_1` FOREIGN KEY (`username`) REFERENCES `utente` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 5. TABELLA PRODOTTICARRELLO
CREATE TABLE `prodotticarrello` (
                                    `idProdCarr` int NOT NULL auto_increment,
                                    `quantita` int DEFAULT NULL,
                                    `idCarrello` int DEFAULT NULL,
                                    `idProdotto` int DEFAULT NULL,
                                    `taglia` varchar(3) default NULL,
                                    PRIMARY KEY (`idProdCarr`),
                                    KEY `idProdotto` (`idProdotto`),
                                    KEY `idCarrello` (`idCarrello`),
                                    CONSTRAINT `prodotticarrello_ibfk_1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE CASCADE,
                                    CONSTRAINT `prodotticarrello_ibfk_2` FOREIGN KEY (`idCarrello`) REFERENCES `carrello` (`idCarrello`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 6. TABELLA ORDINE
CREATE TABLE `ordine` (
                          `idOrdine` int NOT NULL auto_increment,
                          `PrezzoTotale` double DEFAULT NULL,
                          `dataOrdine` date DEFAULT NULL,
                          `metodoDiPagamento` varchar(60) DEFAULT NULL,
                          `indirizzoSpedizione` varchar(60) DEFAULT NULL,
                          `username` varchar(45) DEFAULT NULL,
                          `idCarrello` int DEFAULT NULL,
                          `prodotti` varchar(2000) NOT NULL,
                          PRIMARY KEY (`idOrdine`),
                          KEY `username` (`username`),
                          KEY `idCarrello` (`idCarrello`),
                          CONSTRAINT `ordine_ibfk_1` FOREIGN KEY (`username`) REFERENCES `utente` (`Username`),
                          CONSTRAINT `ordine_ibfk_2` FOREIGN KEY (`idCarrello`) REFERENCES `carrello` (`idCarrello`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 7. TABELLA WISHLIST
CREATE TABLE `wishlist` (
                            `username` varchar(25) NOT NULL,
                            `idProdotto` int NOT NULL,
                            PRIMARY KEY (`username`, `idProdotto`),
                            CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`username`) REFERENCES `utente` (`Username`) ON DELETE CASCADE,
                            CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;