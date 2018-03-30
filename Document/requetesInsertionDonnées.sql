-- script d'insertion de données d'enquête
INSERT INTO `enquete` VALUES (5,'Le décompte final','Fraude','2018-03-07','Paris','En cours',0),
(6,'Le TEST FINAL','Fraude','2018-03-06','Nice','En cours',0),
(7,'El Testo','Vol','2018-03-14','Mexico','En cours',0),
(8,'JiTest','Fraude','2018-03-28','TestLand','Résolue',1),
(9,'Ta','Boulé','2018-03-14','FRANCE','EN cours',0),
(10,'Testons','Vol','2018-03-15','France','En cours',0),
(11,'testo','Vol','2018-03-15','France','En cours',0);

-- script d'insertion de données d'enquêtes archivées 
INSERT INTO `enquete_old` VALUES (1,'Halloween','Homicide','2018-03-21','Montreuil',NULL,0),
(2,'La blague de trop','Harcelement','2017-10-02','Montreuil','Resolue',1),
(3,'Les Noces Pourpres ','Homicide','1989-12-21','Les Jumeaux','Résolue',1),
(4,'Le Test ','Homicide','1989-12-21','Les Jumeaux','Résolue',1);

-- script d'insertion de données d'humain
INSERT INTO `humain` VALUES (1,'Toto','Tutu','1989-07-26','M',NULL,NULL,'1 rue du postier',NULL,NULL,NULL,NULL,NULL,1.7,80,'blague idiote',NULL,1,2,'Mauvaise blague','Belgique'),
(2,'Myers','Mickael','1983-06-23','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,5,'Homicide','USA'),
(3,'Balmine','Davy','1982-11-05','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1.76,60,'petite moustache','',1,1,'agression au parapluie','France'),
(4,'Némar','Jean','2018-03-21','M',NULL,NULL,'MonQ',NULL,NULL,NULL,NULL,NULL,1.78,98,'Etoile rouge',NULL,1,2,'Vol','Russie'),
(5,'Tutu','Toti','1989-07-26','M',NULL,NULL,'1 rue du cil',NULL,NULL,NULL,NULL,NULL,1.7,80,'blague idiote',NULL,1,2,'Vol qualifié','Belgique'),
(6,'postit','mae','2018-03-28','F',NULL,NULL,'1000 rue en bois',NULL,NULL,NULL,NULL,NULL,1.68,59,'très jolie',NULL,0,0,'Fraude','martienne'),
(7,'Test','Testo','2018-03-28','M',NULL,NULL,'1 rue du test',NULL,NULL,NULL,NULL,NULL,1.87,89,'Testeur',NULL,0,0,'Testeur Fou','France'),
(8,'Guignol','Robin','2018-02-27','M',NULL,NULL,'Tester',NULL,NULL,NULL,NULL,NULL,1.76,67,'lol',NULL,1,2,'Prostitution','France'),
(10,'JiTest','Lelien','2018-03-13','M',NULL,NULL,'1 rue du test',NULL,NULL,NULL,NULL,NULL,1.89,74,'Testoide',NULL,1,1,'Defaut de comparaitre','France'),
(11,'Lataupe','Rene','2018-03-14','M',NULL,NULL,'Le terrier',NULL,NULL,NULL,NULL,NULL,1.2,45,'Poilu',NULL,1,2,'voies de fait graves','France'),
(16,'Labricole','Joe',NULL,'M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,0,NULL,NULL),
(18,'Kain','Horatio','1976-05-23','M',NULL,NULL,NULL,'Lieutenant','gestion d\'équipe','2001-07-26','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(19,'De La Fuente','Diego','1986-06-27','M',NULL,NULL,NULL,'Detective','ballistique','2009-09-12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

-- script d'insertion de données dans la table de jointure
INSERT INTO `personne_impliquee` VALUES (5,5,5,2),(32,16,5,2),(39,18,6,1),(40,19,7,1);

-- script d'insertion de données dans la table des codes de statut
INSERT INTO `status` VALUES (1,'agent'),(2,'suspect'),(3,'victime'),(4,'temoin');