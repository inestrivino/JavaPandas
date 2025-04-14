# JavaPandas
[![Unit Tests](https://github.com/inestrivino/JavaPandas/actions/workflows/maven.yml/badge.svg)](https://github.com/inestrivino/JavaPandas/actions)

## Information basique

## Table des contenus

- [JavaPandas](#javapandas)
  - [Information basique](#information-basique)
  - [Table des contenus](#table-des-contenus)
  - [Fonctionnalités principales](#fonctionnalités-principales)
  - [Instalation et utilisation](#instalation-et-utilisation)
  - [Workflow et revue du code](#workflow-et-revue-du-code)
  - [Outils utilisés](#outils-utilisés)
  - [Feedback](#feedback)
  - [Documentation](#documentation)

## Fonctionnalités principales

<!---Description of functionalities offered by our library--->

## Instalation et utilisation

<!---How to install and use the library based on the public snapshot--->
<!---In this section, include existing docker images with a description, and a link to where they are published--->

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

## Feedback

<!---Description of our experience using the outils we chose for the project--->

## Documentation

La totalité de la documentation technique de la librairie peut-être trouvé dans le site officiel: [Documentation JavaPandas](https://inestrivino.github.io/JavaPandas/apidocs/org/JavaPandas/package-summary.html).