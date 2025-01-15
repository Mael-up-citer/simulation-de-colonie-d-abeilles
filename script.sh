#!/bin/bash

# Définir les chemins relatifs
SRC_DIR="src"       # Répertoire des fichiers source Java
BIN_DIR="bin"       # Répertoire pour les fichiers compilés
MAIN_CLASS="App"    # Classe principale à exécuter

# Vérification du répertoire de travail
cd "$(dirname "$0")" || exit 1

# Création du répertoire bin s'il n'existe pas
if [ ! -d "$BIN_DIR" ]; then
  mkdir "$BIN_DIR"
fi

# Compilation des fichiers Java
echo "Compilation des fichiers Java..."
javac -d "$BIN_DIR" "$SRC_DIR"/*.java

# Vérification si la compilation a réussi
if [ $? -ne 0 ]; then
  echo "Erreur lors de la compilation. Vérifiez votre code source."
  exit 1
fi

# Exécution de la classe principale
echo "Exécution de la classe principale ($MAIN_CLASS)..."
java -cp "$BIN_DIR" "$MAIN_CLASS"

# Nettoyage des fichiers compilés
echo "Nettoyage du répertoire $BIN_DIR..."
rm -rf "$BIN_DIR"

if [ $? -eq 0 ]; then
  echo "Nettoyage terminé avec succès."
else
  echo "Erreur lors du nettoyage du répertoire $BIN_DIR."
fi