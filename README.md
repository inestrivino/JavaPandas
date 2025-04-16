# JavaPandas
[![Unit Tests](https://github.com/inestrivino/JavaPandas/actions/workflows/maven.yml/badge.svg)](https://github.com/inestrivino/JavaPandas/actions)

## Information basique

Lien du site : https://inestrivino.github.io/JavaPandas/

## Table des contenus

- [JavaPandas](#javapandas)
  - [Information basique](#information-basique)
  - [Table des contenus](#table-des-contenus)
  - [Fonctionnalités principales](#fonctionnalités-principales)
  - [Instalation et utilisation](#instalation-et-utilisation)
  - [Workflow et revue du code](#workflow-et-revue-du-code)
  - [Outils utilisés](#outils-utilisés)
  - [Déroulment du projet / Feedback sur les outils](#déroulment-du-projet--feedback-sur-les-outils)
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

## Déroulement du projet / Feedback sur les outils

Certains des outils que nous avons décidé d'utiliser, comme Maven, nous étaient familiers et donc simples et fluides à utiliser. 

En revanche, nous avons eu beaucoup de mal à maintenir le flux de travail intact en raison de notre inexpérience. Nous avons bien créé des branches de fonctionnalités pour toutes les fonctionnalités principales de la bibliothèque. Mais lors du développement, certaines feature branches ont fusionnées avec main puis laissées de côté et plus mises à jour. De plus, des branches ont parfois été créées à partir de ces branches plus à jour. Des conflits sont apparus lorsque nous avons voulu réaliser de nouvelles fusions. Par la suite, ces conflits ont été évités en s'assurant de créer les branches depuis la dernière version du projet, sur la branche main, et non depuis des feature branches précédement fusionnées et laissées à l'abandon.

De plus, nous avons eu quelques difficultés à analyser le rapport JaCoCo pour garantir une couverture des tests acceptable, ainsi qu'à créer la démo Docker.

Au cours du projet, nous nous sommes familiarisés avec la manière de suivre un flux de travail, de tirer le meilleur parti des actions, de toutes les fonctionnalités que GitHub offre pour le DevOps (par exemple, les règles sur les branches) et de Docker lui-même.

Dans l'ensemble, bien qu'il y ait eu des difficultés, nous avons réussi à implémenter une bibliothèque fonctionnelle tout en utilisant les outils de CI/CD au mieux de nos capacités.

## Documentation

La totalité de la documentation technique de la librairie peut-être trouvé dans le site officiel: [Documentation JavaPandas](https://inestrivino.github.io/JavaPandas/apidocs/org/JavaPandas/package-summary.html).