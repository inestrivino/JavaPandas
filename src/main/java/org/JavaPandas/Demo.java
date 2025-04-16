package org.JavaPandas;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        // Create a DataFrame with initial column names and types
        List<String> columnTypes = Arrays.asList("int", "double", "String");
        
        DataFrame df = new DataFrame(columnTypes);

        // Add some rows to the DataFrame
        df.addRow(new String[]{"1", "25.0", "Alice"});
        df.addRow(new String[]{"2", "30.5", "Bob"});
        df.addRow(new String[]{"3", "22.0", "Charlie"});

        // Show the DataFrame
        df.showDataFrame();

        // Perform some operations
        System.out.println("\nSum of 'Age' column: " + df.sum("col1"));
        System.out.println("\nQuery result for ID = ");
        df.query("col0 < col1").showDataFrame();
        System.out.println(df.sum("col0"));
    }
}