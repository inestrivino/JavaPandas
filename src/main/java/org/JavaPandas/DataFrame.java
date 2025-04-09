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
    private String[] columnTypes; //liste des types des colonnes ("int", "double", "String"...)

    /**
     * crée un dataframe avec les types des colonnes uniquement
     */
    public DataFrame(String[] columnTypes) {
        this.columnTypes = columnTypes;
        this.data = new HashMap<>();
        
        //initialisation des noms de colonnes (nom générique col0, col1, ...)
        this.columnNames = new String[columnTypes.length];
        for (int i = 0; i < columnTypes.length; i++) {
            String colName = "col" + i;
            columnNames[i] = colName;
            data.put(colName, new ArrayList<>());  //initialisation de chaque colonne avec une liste vide
        }
    }

    /**
     * charge un DataFrame depuis un fichier CSV
     */
    public DataFrame(String csvName) {
        data = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvName))) {
            // Lecture de la première ligne pour récupérer les noms des colonnes
            String headerLine = reader.readLine();
            if (headerLine == null) throw new RuntimeException("empty csv file");

            //découpage de la ligne d'entête pour extraire les noms des colonnes
            columnNames = headerLine.split(",");
            int numCols = columnNames.length;
            
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
            columnTypes = new String[numCols];
            for (int i = 0; i < numCols; i++) {
                columnTypes[i] = inferType(rawColumns.get(i));  //d'abord inférer le type de chaque colonne
                List<Object> typedCol = castColumn(rawColumns.get(i), columnTypes[i]);  //cast des valeurs
                data.put(columnNames[i], typedCol);  //mettre la colonne dans le dataframe
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * crée un DataFrame à partir d'un sous-ensemble de lignes (index)
     */
    public DataFrame(DataFrame sourceFrame, int[] indices) {
        //on copie d'abord les noms de colonnes et types de colonnes de la frame source
        this.columnNames = sourceFrame.columnNames;
        this.columnTypes = sourceFrame.columnTypes;
        this.data = new HashMap<>();

        // Pour chaque colonne de la frame source, on extrait les valeurs pour les indices spécifiés
        for (String col : sourceFrame.data.keySet()) {
            List<Object> fullCol = sourceFrame.data.get(col);
            List<Object> subCol = new ArrayList<>();
            for (int idx : indices) {
                subCol.add(fullCol.get(idx));  //ajouter les valeurs correspondant aux indices
            }
            data.put(col, subCol);  //mettre la colonne sous-ensemble dans le nouveau DataFrame
        }
    }

    /**
     *Crée un DataFrame à partir d'un sous-ensemble de colonnes (labels)
     */
    public DataFrame(DataFrame sourceFrame, String[] labels) {
        // Initialisation des noms et types de colonnes avec les labels passés en paramètre
        this.columnNames = labels;
        this.columnTypes = new String[labels.length];
        this.data = new HashMap<>();

        // Pour chaque label, on vérifie s'il existe dans la frame mère et on copie les données
        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            if (!sourceFrame.data.containsKey(label)) { //erreur si le label n'existe pas
                throw new IllegalArgumentException("Unknown column: " + label); 
            }
            this.columnTypes[i] = sourceFrame.getColumnType(label);  // Récupérer le type de la colonne
            this.data.put(label, new ArrayList<>(sourceFrame.data.get(label)));  //copierles données
        }
    }

    //pour inférer le type de données d'une colonne (int, double, String)
    private String inferType(List<String> column) {
        boolean isInt = true;
        boolean isDouble = true;

        //test de chaque valeur pour voir si elle peut être convertie en int ou double
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

    //pour récupérer le type de données d'une colonne par son label
    private String getColumnType(String label) {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(label)) return columnTypes[i];
        }
        return null;  //si le label n'existe pas
    }

    /**
     * Pour récupérer les données sous forme de Map
     */
    public Map<String, List<Object>> getData() {
        return data;
    }

    /**
     * affiche le contenu du DataFrame pour débug
     */ 
    public void print() {
        System.out.println(Arrays.toString(columnNames));  //noms des colonnes
        int rows = data.get(columnNames[0]).size();  //nb de lignes dans la première colonne
        for (int i = 0; i < rows; i++) {
            for (String col : columnNames) {
                System.out.print(data.get(col).get(i) + "\t");  //puis afficher chaque valeur ligne par ligne
            }
            System.out.println();
        }
    }

    //CREATION METHODS
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

    //REPRESENTATION METHODS
    @Override
    public void showFirstLines(int n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showFirstLines'");
    }

    @Override
    public void showLastLines(int n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showLastLines'");
    }

    @Override
    public void showDataFrame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showDataFrame'");
    }

    //STATISTICAL CALCULATION METHODS
    @Override
    public void sum(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sum'");
    }

    @Override
    public void mean(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mean'");
    }

    @Override
    public void quantile(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'quantile'");
    }

    @Override
    public void cumsum(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cumsum'");
    }

    @Override
    public void cumprod(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cumprod'");
    }

    //TODO: Mechanism for advanced selection
}