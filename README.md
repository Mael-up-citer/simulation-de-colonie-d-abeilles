# Simulation du Comportement des Abeilles Mellifères

## Description

Ce projet simule le comportement de recherche de nourriture des abeilles mellifères dans un environnement 2D. Les abeilles sont réparties en trois groupes principaux : **éclaireuses**, **employées** et **observatrices**, chacun ayant un rôle spécifique dans l'optimisation de la recherche de nourriture et l'acheminement du pollen vers la ruche. Les abeilles interagissent avec des **sources de pollen** qui sont réparties sur un plateau, et la simulation prend en compte des règles d'exploration, de collecte et de communication.

Le projet est conçu pour offrir une simulation paramétrable avec une interface graphique pour visualiser les interactions des abeilles, les sources de nourriture, ainsi que la dynamique de recherche et de collecte.

## Fonctionnalités

### Paramétrage de la simulation :
- **Nombre d'abeilles par type** : Le nombre d'abeilles dans chaque groupe peut être ajusté, y compris les éclaireuses, les employées et les observatrices.
- **Nombre et qualité des sources de nourriture** : Le nombre de sources de pollen et leur quantité initiale sont définis au démarrage de la simulation.
- **Limite maximale d'exploration pour chaque source** : Chaque source de pollen a un nombre limité d'explorations autorisées avant d'être considérée comme épuisée.

### Visualisation graphique :
- **Plateau 2D** : Un plateau est affiché pour représenter les sources de nourriture, les abeilles et la ruche. Les sources de nourriture sont visualisées par des points ou des cercles colorés, représentant leur qualité (quantité de pollen).
- **Mouvements des abeilles** : Le mouvement des abeilles (éclaireuses, employées et observatrices) est mis à jour en temps réel sur le plateau.
- **Représentation de la qualité des sources** : Les sources de nourriture peuvent être colorées selon leur quantité de pollen restante, ce qui reflète leur qualité.

### Comportements simulés :
- **Éclaireuses** : Ces abeilles explorent aléatoirement le plateau pour découvrir de nouvelles sources de nourriture. Une fois qu'une source est trouvée, elles la communiquent aux autres abeilles.
- **Employées** : Les abeilles employées évaluent la qualité de leur source assignée. Si elles trouvent une meilleure source dans leur voisinage, elles mettent à jour leur source de collecte.
- **Observatrices** : Les abeilles observatrices choisissent une source en fonction des informations collectées par les éclaireuses et des danses des employées. Une fois une source choisie, elles adoptent le comportement des employées et explorent son voisinage pour l'améliorer.

## Règles de Simulation

### Éclaireuses :
- Les éclaireuses se déplacent de manière aléatoire à la recherche de nouvelles sources de pollen.
- Lorsqu'elles trouvent une source, elles l'ajoutent à leur "répertoire" et la signalent aux autres abeilles.
- Si une source atteint sa limite d'exploration (définie par le nombre maximal d'explorations), elle est supprimée du plateau et une nouvelle recherche est lancée.

### Employées :
- Les abeilles employées évaluent la qualité des sources qui leur sont assignées.
- Elles peuvent chercher de meilleures sources dans leur voisinage. Si une source de meilleure qualité est trouvée, elles mettent à jour leur assignation.

### Observatrices :
- Les abeilles observatrices ne cherchent pas activement de sources, mais elles sélectionnent une source en fonction des informations récoltées par les éclaireuses, notamment les danses effectuées par les employées.
- Une fois une source choisie, elles adoptent un comportement similaire à celui des employées en cherchant à optimiser la collecte de pollen.

## Arrêt de la Simulation

La simulation peut s'interrompre sous les conditions suivantes :
1. **Objectif atteint** : La meilleure source de nourriture est trouvée et l'exploration est terminée.
2. **Diminution négligeable des gains** : Les gains des nouvelles sources deviennent insignifiants par rapport à une tolérance définie, ce qui indique que la recherche de nourriture ne génère plus de bénéfices significatifs.

## Prérequis

- **Langage** : Java 8 ou supérieur.
- **Bibliothèque graphique** (si applicable) : Pour visualiser les mouvements des abeilles et des sources de nourriture, une bibliothèque graphique telle que **JavaFX** peut être utilisée.

## Structure du Projet

Le projet est composé de plusieurs classes Java, chacune gérant des aspects spécifiques de la simulation :

- **`Ruche`** : Représente la ruche et gère la population d'abeilles (éclaireuses, employées, observatrices).
- **`Source`** : Représente une source de pollen, avec des caractéristiques comme la quantité de pollen disponible et le nombre maximal d'explorations autorisées.
- **`Tableau`** : Gère l'ensemble du plateau de simulation, avec les abeilles et les sources réparties sur une grille 2D.

## Comment Exécuter la Simulation

1. Clonez ou téléchargez le projet.
2. Compilez le code Java en utilisant une commande comme `javac` ou un environnement de développement intégré (IDE) comme **IntelliJ IDEA** ou **Eclipse**.
3. Exécutez la classe principale pour démarrer la simulation.

Exemple de commande en ligne pour exécuter la simulation (en supposant que `Simulation.java` est la classe principale) :

```bash
javac Simulation.java
java Simulation
```
