# Projet Csi miami
### Toutes les images présentes ainsi que d'autres support qui ont permit le developpement du projet sont disponibles dans le dossier Document du projet
### Presentation et resumé des fonctionnalités
A l'attention du client,
Le logiciel permet de consulter , creer et mettre à jour des suspects, des enquetes, et des agents et de gérer l'impliquation de chacun des acteurs dans les enquetes. L'implementation de fonctionnalités similaires pour pour les armes, les vehicules, les preuves materiels pourrait être prevues pour des versions futures.
L'authentification est par défaut désactivé , mais un systeme simulant un login est implementé dans l'interface.
A partir de la page d'acceuil l'utilisateur peut acceder au menu icone en haut à gauche de l'ecran qui permet d'acceder à la partie `Recherche`.L'utilisateur peut alors naviguer via les boutons `Rechercher` / `Detail` / `Administration` dans la barre de menu principale.
Depuis la partie `Recherche` l'utilisateur peut rechercher une liste d'affaire (fetch Case List),
une liste de suspect (Fetch Suspect List) ou une liste d'utilisateurs (Fetch User list).
Depuis un des tableaux qui s'affiche l'utilisateur peut consulter une fiche détaillée (`Acceder`).
Depuis la fiche `Detail` l'utilisateur peut mettre à jour ou completer les informations via l'icone editer ( en forme de crayon ) ou archiver une fiche (`Archiver`).
Depuis la partie `Administration` l'utilisateur peut creer ou mettre à jour une fiche suspect, utilisateur ou enquete.

## MODE OPÉRATOIRE

### Prérequis (Back)
- Java 1,8
- Maven (+ commandes exécuter/déployer)
- Git
- SGBDR MySQL
- scriptDatabaseCsi.sql
- requetesInsertionDonnées.sql (obtention des données)

#### Modifications nécessaires (Java : /CSI-Back/src/main/resources/application.properties)
- spring.datasource.url=jdbc:mysql :
- spring.datasource.username
- spring.datasource.password

- Si besoin de changer le port du serveur Tomcat,  ajouter la ligne : 
server.port: XXXX

### BASE DE DONNEE
La modelisation et la conception de la base de donnée ont été realisé avec  le SGBDR (Système de Gestion de Bases de Données Relationnelles) MySql.
`SGBDR` : En informatique, un système de gestion de base de données (abr. SGBD) est un logiciel système destiné à stocker et à partager des informations dans une base de données, en garantissant la qualité, la pérennité et la confidentialité des informations, tout en cachant la complexité des opérations.

### SCRIPTS SQL
- Document/`scriptDataBaseCsi.sql`
Ouvrir le fichier scripdDatabaseCsi.sql dans un logiciel de gestion de base de données puis l'executer pour génerer la base de donnée.
exemple pour Workbench : Cliquer sur file > Open Sql Script > Selectionner toutes les requetes et cliquer sur executer ( l'icone en forme d'eclair jaune).
- Rappel : Pour génerer un jeux de données, le fichier `requetesInsertionDonnées.sql` est nécessaire et bien présent dans le projet (/Fil_Rouge/src/main/resources/data.sql) :
-> Génération d'enquetes.
-> Génération d'agents , de suspects .
-> Génération des codes des statuts ( agent , temoin , suspect ..)
-> Génération d'archive d'enquêtes.

### SCHEMA SQL
Document/schemaDB.png
![](Document/schemaDB.png)

## Installation

#### Back
Dans un repertoire de votre choix ouvrir `GitBash` et entrer la commande `git clone https://github.com/jipaow/CSI-Back.git`.

### GENERATION DU FICHIER Csiback-0.0.1-SNAPSHOT.jar
 pour génerer le fichier .jar executable :
 - se placer dans le repertoire du projet (CSI-Back/CSI-Back) 
 - ouvrir un invité de commande à cet emplacement
 - taper la commande `mvnw package` ( si le serveur du projet est lancé) ou `mvnw pacakge -DskipTests` (si le serveur du projet n'est pas lancé le build ne se fera pas)
 -le fichier Csiback-0.0.1-SNAPSHOT.jar est génerer dans le dossier target

### LANCEMENT DU SERVEUR
1. Ouvrir une invite de commande dans le dossier contenant le fichier jar ( le dossier target )
2. Entrer : ```java -jar Csiback-0.0.1-SNAPSHOT.jar```

## DIAGRAMMES

### USECASE

- USECASE DE L’APPLICATION

![](Document/UseCase.png)

-DIAGRAMME DE PACKAGE DE FONCTIONNALITES

![](Document/DiagrammePackageFonctionnalités.png)

### DIAGRAMMES D’ACTIVITE

- CREATION DE SUSPECT

![](Document/CreationFicheSuspect.png)

- CREATION D'UNE AFFAIRE

![](Document/CreationdFicheAffaire.png)

- AJOUT D'UN SUSPECT DANS UNE AFFAIRE

![](Document/Ajouter un suspect dans une affaire.png)

- CONSULTATION DE LA FICHE D'UNE ENQUETE

![](Document/DetailEnquete.png)



### DIAGRAMME DE CLASSES

![](Document/diagramme_de_classe.png)

### MOCKUPS

- PAGE DE CONNEXION

![](Document/Mockup/Home_login.png)

- PAGE DE L’ADMINISTRATEUR

![](Document/Mockup/Administration.png)

- PAGES DES ENQUETES

![](Document/Mockup/Detail_1.png)

- CONSULTATION DES AFFAIRES

![](Document/Mockup/Consultation_2.png)

- FICHE SUSPECT

![](Document/Mockup/Detail_5.png)





____________________________________________________________________________________________________________________________________________
@Author jean-philippe:

### BACKEND
- Package co.simplon.model : Suspect.java  / DataSuspect.java classes ("surclasse" qui permet le typage du modele pour son traitement
par le frontEnd).
- Package co.simplon.dao : SuspectDAO.java Interface definissant les méthodes de traitement de la couche dao pour communiquer avec 
la base de donnée.
                           jdbcSuspectDAO.java Classe qui implémente les methodes de SuspectDAO.java utilise le preparedStatement pour executer les requtes sql (les instances de PreparedStatement contiennent des instructions sql déjà compilées qui améliore notement les preformances, les instructions sql contiennent un ou plusieurs paramètres d'entée) afin de communiquer avec le base de donnée.
- Package co.simplon.service : SuspectService.java Classe qui expose au Controller les méthodes permettant de faire le lien entre 
la couche DAO et le ce dernier.
- Package co.simplon.controller: SuspectController.java permet le mapping des requetes url, classe permettant l'interaction entre le client et le server en traitant les actions de l'utilisateur, modifie les données du modèle et de la vue.
- Package co.simplon.tests ( dans le dossier src/test/java ):  SuspectControllerTest.java teste les methodes getAllSuspect()
et get SuspectbyId() service/controller.
                                 TestDAO.java test les methodes Insert et Update.
ces classes de tests ont été realisé ("pair programming") en collaboration avec Robin.
- Le projet BackEnd contient des packages et classes realisées en commun tel que Security qui met en place la solution d'authentification fournie par Sebastien, ainsi que StorageService, UploadController qui devaient permettre la gestion d'upload et de download de fichier mais qui devront être implemntées dans une V2, Enfin les classes concernant les armes.

### FRONTEND

- Repository CSI-Front est le repository front commun le tableau, formulaires ( creation et update ) et la carte de détail sont basée sur les composants realisés sur une maquette front => https://github.com/jipaow/suspectFront. Ces composants ont été largement remaniés et optimisés par Kayetan.
- Repository suspectFront : Composant (Component) suspect ( tableau de liste des suspects ) / update-suspect ( formulaire d'update des informations d'un suspect) / form-suspect ( formulaire de creation d'un suspect ) / edit-suspect ( carte contenant le détails des infos d'un suspect ainsi que le lien vers le formulaire d'update (d'où le nom de composant pas très judicieux de prime abord) ) / suspect.service.ts classes de service qui permet l'interaction avec la partie server Backend .
les tel qu'app-routing, app-module, suspect.ts .

                                 



