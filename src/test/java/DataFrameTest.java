import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;
import org.JavaPandas.DataFrame;

public class DataFrameTest {

    @Test
    public void testConstructorWithTypes() {
        String[] types = {"int", "String", "double"};
        DataFrame df = new DataFrame(types);
        assertNotNull(df);
    }

    @Test
    public void testConstructorFromCSV() throws IOException {
        //crée un fichier temporaire CSV qui sera supprimé à la fin
        File tempCsv = File.createTempFile("test", ".csv");
        FileWriter writer = new FileWriter(tempCsv);
        writer.write("id,name,score\n");
        writer.write("1,Han,95.2\n");
        writer.write("2,Leia,89.4\n");
        writer.write("3,Luke,76.0\n");
        writer.close();

        //now we try to create the dataframe using the csv file
        DataFrame df = new DataFrame(tempCsv.getAbsolutePath());

        assertNotNull("le DataFrame crée ne doit pas être null",df);
        tempCsv.delete();
    }

    @Test
    public void testConstructorFromCSV2() throws IOException {
        File tempCsv = File.createTempFile("test", ".csv");
        FileWriter writer = new FileWriter(tempCsv);
        writer.write("id,name,score\n");
        writer.write("1,Han,95.2\n");
        writer.write("2,Leia,89.4\n");
        writer.write("3,Luke,76.0\n");
        writer.close();

        DataFrame df = new DataFrame(tempCsv.getAbsolutePath());
        Map<String, List<Object>> data = df.getData();

        assertEquals("le contenu de la colonne du DataFrame est le même que celui du fichier csv",Arrays.asList("Han", "Leia", "Luke"), data.get("name"));
        assertEquals("le contenu de la colonne du DataFrame est le même que celui du fichier csv",Arrays.asList(95.2, 89.4, 76.0), data.get("score"));

        tempCsv.delete();
    }

    @Test
    public void testConstructorFromIndices() {
        String[] types = {"int", "String"};
        DataFrame df = new DataFrame(types);

        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3));
        df.getData().get("col1").addAll(Arrays.asList("Han", "Leia", "Luke"));
        DataFrame subset = new DataFrame(df, new int[]{0, 2});

        assertEquals("Le nouveau DataFrame a le bon nombre de lignes",2, subset.getData().get("col0").size());
        assertEquals("Le nouveau DataFrame a le bon nombre de colonnes",3, df.getData().get("col0").size());
        assertEquals("Le nouveau DataFrame a le bon nombre de lignes",2, subset.getData().get("col1").size());
        assertEquals("Le nouveau DataFrame a le bon nombre de colonnes",3, df.getData().get("col1").size());
    }

    @Test
    public void testConstructorFromIndices2() {
        String[] types = {"int", "String"};
        DataFrame df = new DataFrame(types);

        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3));
        df.getData().get("col1").addAll(Arrays.asList("Han", "Leia", "Luke"));

        DataFrame subset = new DataFrame(df, new int[]{0, 2});
        Map<String, List<Object>> subData = subset.getData();

        assertEquals("les valeurs des colonnes du nouveau DataFrame sont celles extraites du DataFrame source à partir des indices",Arrays.asList(1, 3), subData.get("col0"));
        assertEquals("les valeurs des colonnes du nouveau DataFrame sont celles extraites du DataFrame source à partir des indices",Arrays.asList("Han", "Luke"), subData.get("col1"));
    }

    @Test
    public void testConstructorFromLabels() {
        String[] types = {"int", "String", "double"};
        DataFrame df = new DataFrame(types);

        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3));
        df.getData().get("col1").addAll(Arrays.asList("Han", "Leia", "Luke"));
        df.getData().get("col2").addAll(Arrays.asList(10.5, 20.2, 30.3));

        String[] labels = {"col0", "col2"};
        DataFrame newDf = new DataFrame(df, labels);

        assertNotNull("le DataFrame créé ne doit pas être null",newDf);
    }

    public void testConstructorFromLabels1() {
        String[] types = {"int", "String", "double"};
        DataFrame df = new DataFrame(types);

        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3));
        df.getData().get("col1").addAll(Arrays.asList("Han", "Leia", "Luke"));
        df.getData().get("col2").addAll(Arrays.asList(10.5, 20.2, 30.3));

        String[] labels = {"col0", "col2"};
        DataFrame newDf = new DataFrame(df, labels);

        assertTrue("La nouvelle colonne a le bon label que la colonne source récupérée par son label",newDf.getData().containsKey("col0"));
        assertTrue("La nouvelle colonne a le bon label que la colonne source récupérée par son label",newDf.getData().containsKey("col2"));
        assertFalse("La nouvelle colonne a le bon label que la colonne source récupérée par son label",newDf.getData().containsKey("col1"));
    }

    @Test
    public void testConstructorFromLabels2() {
        String[] types = {"int", "String", "double"};
        DataFrame df = new DataFrame(types);

        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3));
        df.getData().get("col1").addAll(Arrays.asList("Han", "Leia", "Luke"));
        df.getData().get("col2").addAll(Arrays.asList(10.5, 20.2, 30.3));

        String[] labels = {"col0", "col2"};
        DataFrame newDf = new DataFrame(df, labels);
        Map<String, List<Object>> newData = newDf.getData();

        assertEquals("La colonne du nouveau DataFrame a les mêmes valeurs que la colonne source récupérée par son label",Arrays.asList(1, 2, 3), newData.get("col0"));
        assertEquals("La colonne du nouveau DataFrame a les mêmes valeurs que la colonne source récupérée par son label",Arrays.asList(10.5, 20.2, 30.3), newData.get("col2"));
    }

    @Test
    public void testAddRow() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, df::addRow);
    }

    @Test
    public void testAddCol() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.addCol("newCol"));
    }

    @Test
    public void testShowFirstLines() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.showFirstLines(5));
    }

    @Test
    public void testShowLastLines() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.showLastLines(5));
    }

    @Test
    public void testShowDataFrame() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, df::showDataFrame);
    }

    @Test
    public void testSum() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.sum("label"));
    }

    @Test
    public void testMean() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.mean("label"));
    }

    @Test
    public void testQuantile() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.quantile("label"));
    }

    @Test
    public void testCumsum() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.cumsum("label"));
    }

    @Test
    public void testCumprod() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.cumprod("label"));
    }
}

