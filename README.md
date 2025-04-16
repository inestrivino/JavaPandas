# JavaPandas
[![Unit Tests](https://github.com/inestrivino/JavaPandas/actions/workflows/maven.yml/badge.svg)](https://github.com/inestrivino/JavaPandas/actions)

## Information basique

Cette bibliothèque Java implémente une partie des fonctionnalités de la [bibliothèque Python du même nom](https://github.com/pandas-dev/pandas). Elle a été créée dans le cadre d'un projet étudiant à l'Université Grenoble Alpes.

Lien du site : https://inestrivino.github.io/JavaPandas/

## Table des contenus

- [JavaPandas](#javapandas)
  - [Information basique](#information-basique)
  - [Table des contenus](#table-des-contenus)
  - [Fonctionnalités principales](#fonctionnalités-principales)
  - [Installation et utilisation](#installation-et-utilisation)
  - [Workflow et revue du code](#workflow-et-revue-du-code)
  - [Outils utilisés](#outils-utilisés)
  - [Github Actions](#github-actions)
  - [Déroulement du projet et feedback sur les outils](#déroulement-du-projet-et-feedback-sur-les-outils)
  - [Documentation](#documentation)

## Fonctionnalités principales

- Création d'un DataFrame
  - à partir d'une liste de types de colonnes
  - à partir d'un fichier csv
  - à partir d'index de lignes d'un DataFrame source
  - à partir de labels de colonnes d'un DataFrame source
- Affichage d'un DataFrame
  - afficher les n premières lignes
  - afficher les n dernières lignes
  - afficher l'entièreté du contenu
- Ajout de lignes et de colonnes dans un DataFrame
- Opération statistiques sur un DataFrame
  - somme des valeurs d'une colonne
  - moyenne d'une colonne
  - somme cumulée d'une colonne
  - produit cumulé d'une colonne
  - maximum d'une colonne
  - minimum d'une colonne
- Sélection des colonnes qui vérifient une condition booléenne
- Récupérer les labels et les types des colonnes d'un DataFrame
- Récupérer les données stockées dans un DataFrame

## Installation et utilisation

Pour installer la bibliothèque à partir du snapshot public, suivez ces étapes :
1. Ajouter la dépendance à votre projet Maven: Incluez la dépendance de la bibliothèque dans votre fichier pom.xml.
```xml
<dependencies>
    <dependency>
      <groupId>devops_projet</groupId>
      <artifactId>javapandas</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```
2. Installer les dépendances: Exécutez la commande suivante pour installer les dépendances.
```bash
mvn install
```

Nous fournissons également des images Docker préconfigurées pour faciliter l'utilisation et comprobation de la bibliothèque. Voici l'images disponibles:
```
docker pull ghcr.io/inestrivino/javapandas-demo:latest
```

## Workflow et revue du code

Pour contribuer au projet, il est nécessaire d'utiliser [git](https://git-scm.com/) et d'avoir un compte GitHub.
JavaPandas utilise le workflow `Feature Branch` (plus d'informations [ici](https://git-scm.com/book/ms/v2/Git-Branching-Branching-Workflows)). Pour contribuer au projet, suivez ces étapes :

1. Fork le projet, en choisissant d'inclure toutes les branches.
2. Assurez-vous que votre branche locale `main` est à jour avec le dépôt actuel de JavaPandas.
3. Créez maintenant une nouvelle feature branch dans laquelle apporter vos modifications.

```bash
git checkout -b shiny-new-feature
```

4. Effectuez vos modifications dans la nouvelle branche.
5. Poussez vos modifications pour qu'elles apparaissent publiquement dans votre fork GitHub du projet.

```bash
git push origin shiny-new-feature
```

6. Dans votre dépôt GitHub, cliquez sur `Compare and pull request`. Écrivez un titre descriptif et une description de vos modifications.
7. Cliquez sur `Send Pull Request`.

Une fois votre code soumis et après avoir passé tous les tests, il sera examiné selon notre liste de vérification.

<details>
  <summary>Liste de vérification pour le code review</summary>

- [ ] Le code fonctionne-t-il ?
- [ ] Répond-il à l'objectif ?
- [ ] La logique est-elle correcte ?
- [ ] Le code est-il facile à comprendre ?
- [ ] Le code est-il conforme aux conventions ?
- [ ] Y a-t-il du code inutile/redondant ?
- [ ] Le code est-il suffisamment modulaire ?
- [ ] Y a-t-il du code introduit pour le débogage qui devrait être supprimé ?
- [ ] Les données d'entrée sont-elles vérifiées ?
- [ ] Les erreurs et exceptions sont-elles traitées ?
- [ ] Le cas de valeurs non valides pour les paramètres est-il traité ?
- [ ] Le travail a-t-il été commenté ? Les commentaires décrivent-ils les intentions ?
- [ ] Toutes les fonctions sont-elles commentées ?
- [ ] La prise en charge des cas pathologiques est-elle documentée ?
- [ ] Du code de test a-t-il été fourni ?
- [ ] Les tests unitaires vérifient-ils que le code répond à l'objectif ?
</details>

## Outils utilisés

- [Maven](https://github.com/apache/maven): Utilisé our compiler et tester le code dans le cadre de l'intégration continue.
- [Github Packages](https://docs.github.com/fr/packages): Utilisé pour deployer un snapshot de notre projet après chaque push vers main.
- [JaCoco](https://github.com/jacoco/jacoco): Utilisé pour vérifier la couverture du code dans le cadre de l'intégration continue.
- [Docker](https://www.docker.com/) et [Github Container Registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry): Utilisés pour créer et deployer l'image du demo du projet.
- [Github Pages](https://pages.github.com/): Utilisé pour le site web et documentation du projet.

## Github Actions

Voici une explication détaillée de nos GitHub Actions pour ce projet :

1. <b>Tests unitaires</b> : Cette action est dédiée à 5 fonctionnalités principales. En plus, un branch ne peut pas merger avec main si elle ne passe pas cette action.
   1. <b>Compilation du projet Maven</b>: via la commande mvn compile.
   2. <b>Exécution des tests</b>: via la commande mvn test.
   3. <b>Validation de la couverture des tests</b>: Tout d'abord, nous exécutons mvn verify et mvn jacoco:report pour produire le rapport de couverture. Ensuite, nous installons libxml2-utils et l'exécutons pour extraire le nombre d'instructions manquées et couvertes selon le rapport JaCoCo. Avec ces informations, nous vérifions si les deux valeurs sont à 0 (auquel cas la couverture est de 100 %, car il n'y a rien à couvrir), si seules les instructions couvertes sont à 0, ou si nous devons calculer le pourcentage à partir des valeurs couvertes et manquées. Enfin, nous nous assurons que la couverture est soit de 80 % ou plus.
   4. <b>Déploiement du package Maven</b>: Une fois que nous avons garanti que la couverture est satisfaisante, nous utilisons mvn deploy pour produire un package à publier.
   5. <b>Déploiement des fichiers pour le site Maven auto-mis à jour.</b>
   6. SUPPLÉMENTAIRE : Le badge en haut de ce README est configuré pour suivre le résultat de cette action, montrant rapidement aux utilisateurs potentiels si la version actuelle est stable et vérifiée.
2. <b>pages-build-deployment</b>: Cette action, dont les informations se trouvent principalement dans la branche gh-pages, est dédiée à la construction et au déploiement automatiques du site web du dépôt, qui inclut la documentation.
3. <b>Docker</b>: Ce workflow construit l'image de la démo et la publie sur le registre de conteneurs GitHub. Il est déclenché par la fin réussie de l'action des tests unitaires (c'est-à-dire qu'il ne s'exécute qu'une fois que nous avons garanti un pourcentage de couverture des tests et une compilation réussie).

## Déroulement du projet et feedback sur les outils

Certains des outils que nous avons décidé d'utiliser, comme Maven, nous étaient familiers et donc simples et fluides à utiliser. 

En revanche, nous avons eu beaucoup de mal à maintenir le flux de travail intact en raison de notre inexpérience. Nous avons bien créé des branches de fonctionnalités pour toutes les fonctionnalités principales de la bibliothèque. Mais lors du développement, certaines feature branches ont fusionnées avec main puis laissées de côté et plus mises à jour. De plus, des branches ont parfois été créées à partir de ces branches plus à jour. Des conflits sont apparus lorsque nous avons voulu réaliser de nouvelles fusions. Par la suite, ces conflits ont été évités en s'assurant de créer les branches depuis la dernière version du projet, sur la branche main, et non depuis des feature branches précédement fusionnées et laissées à l'abandon.

De plus, nous avons eu quelques difficultés à analyser le rapport JaCoCo pour garantir une couverture des tests acceptable, ainsi qu'à créer la démo Docker.

Au cours du projet, nous nous sommes familiarisés avec la manière de suivre un flux de travail, de tirer le meilleur parti des actions, de toutes les fonctionnalités que GitHub offre pour le DevOps (par exemple, les règles sur les branches) et de Docker lui-même.

Dans l'ensemble, bien qu'il y ait eu des difficultés, nous avons réussi à implémenter une bibliothèque fonctionnelle tout en utilisant les outils de CI/CD au mieux de nos capacités.

## Documentation

La totalité de la documentation technique de la librairie peut-être trouvé dans le site officiel: [Documentation JavaPandas](https://inestrivino.github.io/JavaPandas/apidocs/org/JavaPandas/package-summary.html).