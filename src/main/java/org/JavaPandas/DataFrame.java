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
    private List<String> columnNames;
    private List<String> columnTypes;

    /*------CONSTRUCTEURS------*/
    /**
     * crée un dataframe avec les types des colonnes uniquement
     * @param columnTypes une liste de types
     */
    public DataFrame(List<String> columnTypes) {
        this.data = new HashMap<>();
        
        //initialisation des noms de colonnes (nom générique col0, col1, ...)
        this.columnNames = new ArrayList<>(columnTypes.size());
        for (int i = 0; i < columnTypes.size(); i++) {
            String colName = "col" + i;
            List<String> values = new ArrayList<>();
            this.addCol(colName, columnTypes.get(i), values);
        }
    }

    /**
     * Construit un DataFrame à partir d'un fichier CSV
     * @param csvName le chemin d'un fichier CSV
    */
    public DataFrame(String csvName) {
        data = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvName))) {
            // Lecture de la première ligne pour récupérer les noms des colonnes
            String headerLine = reader.readLine();
            if (headerLine == null) throw new RuntimeException("empty csv file");

            //découpage de la ligne d'entête pour extraire les noms des colonnes
            for(String s : headerLine.split(",")) this.columnNames.add(s);
            int numCols = this.columnNames.size();
            
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
            this.columnTypes = new ArrayList<>(numCols);
            for (int i = 0; i < numCols; i++) {
                columnTypes.set(i, inferType(rawColumns.get(i)));  //d'abord inférer le type de chaque colonne
                List<Object> typedCol = castColumn(rawColumns.get(i), columnTypes.get(i));  //cast des valeurs
                data.put(columnNames.get(i), typedCol);  //mettre la colonne dans le dataframe
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //crée un DataFrame à partir d'un sous-ensemble de lignes (index)
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

    //Crée un DataFrame à partir d'un sous-ensemble de colonnes (labels)
    public DataFrame(DataFrame sourceFrame, String[] labels) {
        // Initialisation des noms et types de colonnes avec les labels passés en paramètre
        for(String s : labels) columnNames.add(s);
        this.columnTypes = new ArrayList<>(columnNames.size());
        this.data = new HashMap<>();

        // Pour chaque label, on vérifie s'il existe dans la frame mère et on copie les données
        for (int i = 0; i < columnNames.size(); i++) {
            if (!sourceFrame.getData().containsKey(columnNames.get(i))) { //erreur si le label n'existe pas
                throw new IllegalArgumentException("Unknown column: " + columnNames.get(i)); 
            }
            this.columnTypes.set(i, sourceFrame.getColumnType(columnNames.get(i)));  // Récupérer le type de la colonne
            this.data.put(columnNames.get(i), new ArrayList<>(sourceFrame.getData().get(columnNames.get(i))));  //copier les données
        }
    }

    /*------AFFICHAGE------*/
    @Override
    public void showFirstLines(int n) {
        // Affiche l'entête
        System.out.println(Arrays.toString(columnNames.toArray()));
        // Détermine le nombre de lignes à afficher (au max le nombre total de lignes)
        int rows = data.get(columnNames.get(0)).size();
        int limit = Math.min(n, rows);
        for (int i = 0; i < limit; i++) {
            for (String col : columnNames) {
                System.out.print(data.get(col).get(i) + "\t");
            }
            System.out.println();
        }
    }

    @Override
    public void showLastLines(int n) {
        // Affiche le fin
        System.out.println(Arrays.toString(columnNames.toArray()));
        int rows = data.get(columnNames.get(0)).size();
        int start = Math.max(0, rows - n); // Si n > rows, commence à 0
        for (int i = start; i < rows; i++) {
            for (String col : columnNames) {
                System.out.print(data.get(col).get(i) + "\t");
            }
            System.out.println();
        }
    }

    @Override
    public void showDataFrame() {
        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(columnNames.toArray())).append("\n");
        int rows = data.get(columnNames.get(0)).size();
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
    public void addRow(Object[] values){
        if(values.length != columnNames.size()) throw new IllegalArgumentException("Number of values inputed must be the same as the number of columns");
        for(int i=0; i<values.length; i++){
            if(!belongsInColumn(values, columnTypes.get(i))){
                throw new IllegalArgumentException("Cannot insert this element into this column");
            }
            data.get(columnNames.get(i)).add(values[i]);
        }
    }

    @Override
    //adds columns into the dataframe, by using the name, type, and a string of values to insert
    public void addCol(String label, String type, List<String> values){
        //we create the values as they should be
        List<Object> realValues = castColumn(values, type);

        //we add the new label and type
        columnNames.add(label);
        columnTypes.add(type);
        
        //we insert the new column
        data.put(label, realValues);
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

    /*-----QUERY-----*/
    //advanced selection method based on a condition
    public void query(String condition){
        String[] parsedCondition = parseCondition(condition);
        String column1 = parsedCondition[0];
        String column2 = parsedCondition[2];
        String[] columnsQuery = {column1, column2};

        //once parsed we apply the selection
        int sizeToCheck = Math.min(data.get(column1).size(), data.get(column2).size());
        DataFrame result = new DataFrame(this, columnsQuery);

        List<Object> column1Data = data.get(column1);
        List<Object> column2Data = data.get(column2);

        switch (parsedCondition[1]) {
            case "==":
                for(int i=0; i<sizeToCheck; i++){
                    if(column1Data.get(i)==column2Data.get(i)){
                        Object[] elements = {column1Data.get(i), column2Data.get(i)};
                        result.addRow(elements);
                    }
                }
                break;
            case "<":
                for(int i=0; i<sizeToCheck; i++){
                    if((Double)column1Data.get(i)<(Double)column2Data.get(i)){
                        Object[] elements = {column1Data.get(i), column2Data.get(i)};
                        result.addRow(elements);
                    }
                }
                break;
            case ">":
                for(int i=0; i<sizeToCheck; i++){
                    if((Double)column1Data.get(i)>(Double)column2Data.get(i)){
                        Object[] elements = {column1Data.get(i), column2Data.get(i)};
                        result.addRow(elements);
                    }
                }
                break;
            case "<=":
                for(int i=0; i<sizeToCheck; i++){
                    if((Double)column1Data.get(i)<=(Double)column2Data.get(i)){
                        Object[] elements = {column1Data.get(i), column2Data.get(i)};
                        result.addRow(elements);
                    }
                }  
                break;
            case ">=":
                for(int i=0; i<sizeToCheck; i++){
                    if((Double)column1Data.get(i)>=(Double)column2Data.get(i)){
                        Object[] elements = {column1Data.get(i), column2Data.get(i)};
                        result.addRow(elements);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + parsedCondition[1]);
        }

        result.showDataFrame();
    }

    /*-----GETTERS-----*/
    //get the names of columns
    public List<String> getColumnNames(){
        return this.columnNames;
    }

    //get the types of columns
    public List<String> getColumnTypes(){
        return this.columnTypes;
    }

    //get the full data of the dataframe
    public Map<String, List<Object>> getData(){
        return this.data;
    }

    /*-----PRIVATE METHODS-----*/
    //serves to parse the condition for the query and check that the query is applicable
    private String[] parseCondition(String condition){
        //we start by parsing the condition
        //we assume conditions are of the type "'column1' 'operator' 'column2'"
        //operators may be: < <= > >= ==
        String[] parts = condition.split("\\s+");    
        if(parts.length != 3) throw new IllegalArgumentException("The query condition could not be parsed into two columns and an operator");
        String column1 = parts[0];
        String column2 = parts[2];

        //we check that both columns are not the same
        if(column1.equals(column2)) throw new IllegalArgumentException("Cannot compare column with itself");
        //we check that both columns exist in the dataframe and that they are int or double type
        String typeCol1 = getColumnType(column1);
        String typeCol2 = getColumnType(column2);
        if(typeCol1==null || typeCol2==null){
            throw new IllegalArgumentException("Columns do not exist");
        }
        if(typeCol1=="String" || typeCol2=="String"){
            throw new IllegalArgumentException("String type columns cannot be compared");
        }

        String[] parsed = {column1, parts[1], column2};
        return parsed;
    }

    //pour récupérer le type de données d'une colonne par son label
    private String getColumnType(String label) {
        for (int i = 0; i < columnNames.size(); i++) {
            if (columnNames.get(i).equals(label))return columnTypes.get(i);
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

    private boolean belongsInColumn(Object value, String type){
        return (value instanceof Integer && type.equals("int")) || (value instanceof Double && type.equals("double")) || (value instanceof String && type.equals("String"));
    }

    //caste une colonne en fonction de son type
    private List<Object> castColumn(List<String> rawCol, String type) {
        List<Object> resultat = new ArrayList<>();
        for (String val : rawCol) {
            //conversion des types
            switch (type) {
                case "int":
                    try{
                    resultat.add(Integer.parseInt(val));
                    }
                    catch(NumberFormatException e){
                        throw new NumberFormatException("Value could not be parsed to column type");
                    }
                    break;
                case "double":
                try{
                    resultat.add(Double.parseDouble(val));
                    }
                    catch(NumberFormatException e){
                        throw new NumberFormatException("Value could not be parsed to column type");
                    }
                    break;
                default://string
                    resultat.add(val);
            }
        }
        return resultat;
    }
}