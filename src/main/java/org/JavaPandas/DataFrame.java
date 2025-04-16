package org.JavaPandas;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFrame implements DataFrameInterface{    
    private Map<String, List<Object>> data; // map pour les colonnes : associe chaque label à une liste d'objets
    private String[] columnNames;
    private String[] columnTypes;

    /*------CONSTRUCTEURS------*/
    /**
     * Crée un dataframe avec les types des colonnes uniquement.
     * @param columnTypes une liste de types
     */
    public DataFrame(String[] columnTypes) {
        this.columnTypes = columnTypes;
        this.data = new HashMap<>();
        
        //initialisation des noms de colonnes (nom générique col0, col1, ...)
        this.columnNames = new String[columnTypes.length];
        for (int i = 0; i < columnTypes.length; i++) {
            String colName = "col" + i;
            this.columnNames[i] = colName;
            data.put(colName, new ArrayList<>());
        }
    }

    /**
     * Construit un DataFrame à partir d'un fichier CSV.
     * @param csvName le chemin d'un fichier CSV
     */
    public DataFrame(String csvName) {
        data = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvName))) {
            // Lecture de la première ligne pour récupérer les noms des colonnes
            String headerLine = reader.readLine();
            if (headerLine == null) throw new RuntimeException("empty csv file");

            //découpage de la ligne d'entête pour extraire les noms des colonnes
            this.columnNames = headerLine.split(",");
            int numCols = this.columnNames.length;
            
            //pour stocker les données brutes de chaque colonne
            List<List<String>> rawColumns = new ArrayList<>();
            for (int i = 0; i < numCols; i++) {
                rawColumns.add(new ArrayList<>());
            }

            //lecture du fichier CSV ligne par ligne
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    rawColumns.get(i).add(values[i].trim());  // Ajout des valeurs dans les colonnes
                }
            }

            //inférer les types des colonnes en fonction des données
            this.columnTypes = new String[numCols];
            for (int i = 0; i < numCols; i++) {
                columnTypes[i] = inferType(rawColumns.get(i));  //d'abord inférer le type de chaque colonne
                List<Object> typedCol = castColumn(rawColumns.get(i), columnTypes[i]);  //cast des valeurs
                data.put(columnNames[i], typedCol);  //mettre la colonne dans le dataframe
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur CSV", e);
        }
    }

    /**
     * Crée un DataFrame à partir d'index de lignes d'un DataFrame source.
     * @param sourceFrame le DataFrame source
     * @param indices une liste d'indices de lignes
     */
    public DataFrame(DataFrame sourceFrame, int[] indices) {
        //on copie d'abord les noms de colonnes et types de colonnes de la frame source
        this.columnNames = sourceFrame.getColumnNames();
        this.columnTypes = sourceFrame.getColumnTypes();
        this.data = new HashMap<>();

        // Pour chaque colonne de la frame source, on extrait les valeurs pour les indices spécifiés
        for (String col : columnNames) {
            List<Object> fullCol = sourceFrame.getData().get(col);
            List<Object> subCol = new ArrayList<>();
            for (int idx : indices) {
                subCol.add(fullCol.get(idx));  //ajouter les valeurs correspondant aux indices
            }
            data.put(col, subCol);  //mettre la colonne sous-ensemble dans le nouveau DataFrame
        }
    }

    /**
     * Crée un DataFrame à partir de labels de colonnes d'un DataFrame source.
     * @param sourceFrame le DataFrame source
     * @param labels une liste de labels
     */
    public DataFrame(DataFrame sourceFrame, String[] labels) {
        // Initialisation des noms et types de colonnes avec les labels passés en paramètre
        this.columnNames = labels;
        this.columnTypes = new String[labels.length];
        this.data = new HashMap<>();

        // Pour chaque label, on vérifie s'il existe dans la frame mère et on copie les données
        for (int i = 0; i < columnNames.length; i++) {
            if (!sourceFrame.getData().containsKey(columnNames[i])) { //erreur si le label n'existe pas
                throw new IllegalArgumentException("Unknown column: " + columnNames[i]); 
            }
            this.columnTypes[i] = sourceFrame.getColumnType(columnNames[i]);  // Récupérer le type de la colonne
            this.data.put(columnNames[i], new ArrayList<>(sourceFrame.getData().get(columnNames[i])));  //copier les données
        }
    }

    /*------AFFICHAGE------*/
    /**
     * Affiche les n premières lignes d'un DataFrame.
     * @param n le nombre de lignes à afficher
     */
    @Override
    public void showFirstLines(int n) {
        // Affiche l'entête
        System.out.println(Arrays.toString(columnNames));
        // Détermine le nombre de lignes à afficher (au max le nombre total de lignes)
        int rows = data.get(columnNames[0]).size();
        int limit = Math.min(n, rows);
        for (int i = 0; i < limit; i++) {
            for (String col : columnNames) {
                System.out.print(data.get(col).get(i) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Affiche les n dernières lignes d'un DataFrame.
     * @param n le nombre de lignes à afficher
     */
    @Override
    public void showLastLines(int n) {
        // Affiche le fin
        System.out.println(Arrays.toString(columnNames));
        int rows = data.get(columnNames[0]).size();
        int start = Math.max(0, rows - n); // Si n > rows, commence à 0
        for (int i = start; i < rows; i++) {
            for (String col : columnNames) {
                System.out.print(data.get(col).get(i) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Affiche le DataFrame.
     */
    @Override
    public void showDataFrame() {
        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(columnNames)).append("\n");
        int rows = data.get(columnNames[0]).size();
        for (int i = 0; i < rows; i++) {
            for (String col : columnNames) {
                sb.append(data.get(col).get(i)).append("\t");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    /*------ADDITION------*/
    @Override
    public void addRow(){
        //TODO
        throw new UnsupportedOperationException("Unimplemented method 'addRow'");
    }

    @Override
    public void addCol(String label){
        //TODO
        throw new UnsupportedOperationException("Unimplemented method 'addCol'");
    }

    //STATISTICAL CALCULATION METHODS

    /**
     * Réalise la somme des valeurs d'une colonne du DataFrame.
     * @param label le label de la colonne sur laquelle effectuer la somme
     * @return la somme des valeurs de la colonne identifiée par label
     */
    @Override
    public double sum(String label) {
        List<Object> column = data.get(label);
        String type = getColumnType(label);
        if (type == null || (!type.equals("int") && !type.equals("double"))) {
            throw new IllegalArgumentException("Column must be numeric (int or double).");
        }
        double sum = 0.0;
        for (Object value : column) {
            sum += ((Number) value).doubleValue();
        }
        return sum;
    }
    

    /**
     * Réalise la moyenne des valeurs d'une colonne du DataFrame.
     * @param label le label de la colonne sur laquelle effectuer la moyenne
     * @return la moyenne des valeurs de la colonne identifiée par label
     */
    @Override
    public double mean(String label) {
        List<Object> column = data.get(label);
        String type = getColumnType(label);
        if (type == null || (!type.equals("int") && !type.equals("double"))) {
            throw new IllegalArgumentException("Column must be numeric (int or double).");
        }
        if(column.size() == 0)
            return 0.0;
        double sum = 0.0;
        for (Object value : column) {
            sum += ((Number) value).doubleValue();
        }
        return sum / column.size();
    }

    /**
     * Réalise la somme cumulée d'une colonne du DataFrame.
     * @param label le label de la colonne sur laquelle effectuer la somme cumulée
     * @return un nouveau DataFrame dont la colonne contient la somme cumulée
     */
    @Override
    public DataFrame cumsum(String label) {
        List<Object> column = data.get(label);
        String type = getColumnType(label);
        if (type == null || (!type.equals("int") && !type.equals("double"))) {
            throw new IllegalArgumentException("Column must be numeric (int or double).");
        }
        DataFrame dfCumsum = new DataFrame(this, getColumnNames());
        double cumulativeSum = 0.0;
        for (int i = 0; i < column.size(); i++) {
            cumulativeSum += ((Number) column.get(i)).doubleValue();
            dfCumsum.getData().get(label).set(i, cumulativeSum);
        }/**
     * Réalise la somme cumulée d'une colonne du DataFrame.
     * @param label le label de la colonne sur laquelle effectuer la somme cumulée
     * @return un nouveau DataFrame dont la colonne contient la somme cumulée
     */
        return dfCumsum;
    }

    /**
     * Réalise le produit cumulé d'une colonne du DataFrame.
     * @param label le label de la colonne sur laquelle effectuer la produit cumulé
     * @return un nouveau DataFrame dont la colonne contient le produit cumulé
     */
    @Override
    public DataFrame cumprod(String label) {
        List<Object> column = data.get(label);
        String type = getColumnType(label);
        if (type == null || (!type.equals("int") && !type.equals("double"))) {
            throw new IllegalArgumentException("Column must be numeric (int or double).");
        }
        DataFrame dfCumprod = new DataFrame(this, getColumnNames());
        double cumulativeProd = 1.0;
        for (int i = 0; i < column.size(); i++) {
            cumulativeProd *= ((Number) column.get(i)).doubleValue();
            dfCumprod.getData().get(label).set(i, cumulativeProd);
        }
        return dfCumprod;
    }

    /**
     * Chercher la valeur maximale d'une colonne du DataFrame.
     * @param label le label de la colonne dans laquelle chercher la valeur maximale
     * @return la valeur maximale de la colonne identifiée par label
     */
    @Override
    public double max(String label) {
        List<Object> column = data.get(label);
        String type = getColumnType(label);
        if (type == null || (!type.equals("int") && !type.equals("double"))) {
            throw new IllegalArgumentException("Column must be numeric (int or double).");
        }
        if(column.size() == 0)
            throw new IllegalArgumentException("Column must not be empty");
        double max = ((Number) column.get(0)).doubleValue();
        double courant = 0;
        for (int i = 0; i < column.size(); i++) {
            courant = ((Number) column.get(i)).doubleValue();
            if(courant>max)
                max=courant;
        }
        return max;
    }

    /**
     * Chercher la valeur minimale d'une colonne du DataFrame.
     * @param label le label de la colonne dans laquelle chercher la valeur minimale
     * @return la valeur minimale de la colonne identifiée par label
     */
    @Override
    public double min(String label) {
        List<Object> column = data.get(label);
        String type = getColumnType(label);
        if (type == null || (!type.equals("int") && !type.equals("double"))) {
            throw new IllegalArgumentException("Column must be numeric (int or double).");
        }
        //colonne vide
        if(column.size() == 0)
            throw new IllegalArgumentException("Column must not be empty");
        double min = ((Number) column.get(0)).doubleValue();
        double courant = 0;
        for (int i = 0; i < column.size(); i++) {
            courant = ((Number) column.get(i)).doubleValue();
            if(courant<min)
                min=courant;
        }
        return min;
    }

    //TODO: Mechanism for advanced selection

    /*-----GETTERS-----*/
    /**
     * Get the names of columns.
     * @return un tableau de String contenant les labels des colonnes du DataFrame
     */
    public String[] getColumnNames(){
        return this.columnNames;
    }

    /**
     * Get the types of columns.
     * @return un tableau de String contenant les types des colonnes du DataFrame
     */
    public String[] getColumnTypes(){
        return this.columnTypes;
    }

    /**
     * Get the full data of the dataframe.
     * @return la table de hachage dans laquelle sont stockées les données du DataFrame
     */
    public Map<String, List<Object>> getData(){
        return this.data;
    }

    /*-----PRIVATE METHODS-----*/
    //pour récupérer le type de données d'une colonne par son label
    private String getColumnType(String label) {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(label))return columnTypes[i];
        }
        return null;  //si le label n'existe pas
    }

    //pour inférer le type de données d'une colonne (int, double, String)
    private String inferType(List<String> column) {
        boolean isInt = true;
        boolean isDouble = true;

        //we check that all the values in the column are int or double
        //if one of the elements is of a different type then we have a string column
        for (String val : column) {
            try {
                Integer.parseInt(val);
            } catch (NumberFormatException e) {
                isInt = false;  //Si la conversion échoue, ce n'est pas un entier
            }

            try {
                Double.parseDouble(val);
            } catch (NumberFormatException e) {
                isDouble = false;  //ce n'est pas un double
            }
        }

        //this way we guarantee the column holds one type of object
        if (isInt) return "int";
        else if (isDouble) return "double";
        else return "String";
    }

    //caste une colonne en fonction de son type
    private List<Object> castColumn(List<String> rawCol, String type) {
        List<Object> resultat = new ArrayList<>();
        for (String val : rawCol) {
            //conversion des types
            switch (type) {
                case "int":
                    resultat.add(Integer.parseInt(val));
                    break;
                case "double":
                    resultat.add(Double.parseDouble(val));
                    break;
                default://string
                    resultat.add(val);
            }
        }
        return resultat;
    }
}