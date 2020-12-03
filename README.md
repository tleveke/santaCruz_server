# Serveur DizifyMusic

  - Serveur développé avec Java Spring Boot

### Base de données

- Modifier le application.properties dans le dossier src/main/resources/ avec les identifiants nécessaires pour que le serveur se connecte à votre serveur de base de données
- Préalabelement créé un database dizifymusic puis importer le fichier bddDizify.sql pour le jeu de données

### Installation

- À la racine du projet, faite un maven install :
 ```sh
 $ mvn spring-boot:run
```
- Avant de lancer le serveur, faites l'étape Base de données au-dessus
- Puis vous pouvez lancer le projet avec votre ide ou directement la commande : 
 ```sh
 $ mvn spring-boot:run
```

### Ce qui restait à faire

- Afficher un Top Artiste / Album / Titre en fonction du nombre de Favoris sur la page d’Accueil
- Afficher une zone de recherche dans le haut du site


