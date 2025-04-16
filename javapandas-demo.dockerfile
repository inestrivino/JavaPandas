# Utiliser une image de base avec OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier tous les fichiers sources Java dans le conteneur
COPY src/main/java/org/JavaPandas/DataFrame.java /app/src/main/java/org/JavaPandas/

# Compiler le fichier Java
RUN javac /app/src/main/java/org/JavaPandas/DataFrame.java

# Définir la commande par défaut pour exécuter le programme
CMD ["java", "org.JavaPandas.DataFrame"]