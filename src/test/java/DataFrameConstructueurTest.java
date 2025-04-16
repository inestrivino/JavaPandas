import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import org.JavaPandas.*;

public class DataFrameConstructueurTest {
    @Test
    public void testConstructorWithTypes() {
        List<String> columnTypes = new ArrayList<>(Arrays.asList("int", "String", "double"));
        DataFrame df = new DataFrame(columnTypes);
        assertNotNull("le DataFrame cree ne doit pas etre null",df);
    }

    //cree un fichier temporaire CSV supprime à la fin et construit un dataframe à partir de ce fichier
    private DataFrame creer_dataframe_csv() throws IOException {
        File tempCsv = File.createTempFile("test", ".csv");
        FileWriter writer = new FileWriter(tempCsv);
        writer.write("id,name,score\n");
        writer.write("1,Han,95.2\n");
        writer.write("2,Leia,89.4\n");
        writer.write("3,Luke,76.0\n");
        writer.close();
        DataFrame df = new DataFrame(tempCsv.getAbsolutePath());
        tempCsv.delete();
        return df;
    }

    @Test
    public void testConstructorFromCSV1() throws IOException {
        DataFrame df = creer_dataframe_csv();
        assertNotNull("le DataFrame cree depuis un fichier csv existant ne doit pas etre null",df);
    }

    @Test
    public void testConstructorFromCSV2() throws IOException {
        DataFrame df = creer_dataframe_csv();
        Map<String, List<Object>> data = df.getData();
        assertEquals("le contenu de la colonne du DataFrame est le meme que celui du fichier csv",Arrays.asList(1, 2, 3), data.get("id"));
    }

    @Test
    public void testConstructorFromCSV3() throws IOException {
        DataFrame df = creer_dataframe_csv();
        Map<String, List<Object>> data = df.getData();
        assertEquals("le contenu de la colonne du DataFrame est le meme que celui du fichier csv",Arrays.asList("Han", "Leia", "Luke"), data.get("name"));
    }

    @Test
    public void testConstructorFromCSV4() throws IOException {
        DataFrame df = creer_dataframe_csv();
        Map<String, List<Object>> data = df.getData();
        assertEquals("le contenu de la colonne du DataFrame est le meme que celui du fichier csv",Arrays.asList(95.2, 89.4, 76.0), data.get("score"));
    }

    private DataFrame creer_dataframe_indices(){
        List<String> columnTypes = new ArrayList<>();
        columnTypes.add("int");
        columnTypes.add("String");
        DataFrame df = new DataFrame(columnTypes);
        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3));
        df.getData().get("col1").addAll(Arrays.asList("Han", "Leia", "Luke"));
        return df;
    }

    @Test
    public void testConstructorFromIndices1() {
        DataFrame df = creer_dataframe_indices();
        DataFrame subset = new DataFrame(df, new int[]{0, 2});
        assertEquals("Le nouveau DataFrame a le bon nombre de lignes",df.getData().get("col0").size()-1,subset.getData().get("col0").size());
    }

    @Test
    public void testConstructorFromIndices2() {
        DataFrame df = creer_dataframe_indices();
        DataFrame subset = new DataFrame(df, new int[]{0, 2});
        assertEquals("Le nouveau DataFrame a le bon nombre de lignes",df.getData().get("col1").size()-1, subset.getData().get("col1").size());
    }

    @Test
    public void testConstructorFromIndices3() {
        DataFrame df = creer_dataframe_indices();
        DataFrame subset = new DataFrame(df, new int[]{0, 2});
        Map<String, List<Object>> subData = subset.getData();
        assertEquals("les valeurs des colonnes du nouveau DataFrame sont celles extraites du DataFrame source à partir des indices",Arrays.asList(1, 3), subData.get("col0"));
    }

    @Test
    public void testConstructorFromIndices4() {
        DataFrame df = creer_dataframe_indices();
        DataFrame subset = new DataFrame(df, new int[]{0, 2});
        Map<String, List<Object>> subData = subset.getData();
        assertEquals("les valeurs des colonnes du nouveau DataFrame sont celles extraites du DataFrame source à partir des indices",Arrays.asList("Han", "Luke"), subData.get("col1"));
    }

    private DataFrame creer_dataframe_labels(){
        List<String> columnTypes = new ArrayList<>(Arrays.asList("int", "String", "double"));
        DataFrame df = new DataFrame(columnTypes);
        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3));
        df.getData().get("col1").addAll(Arrays.asList("Han", "Leia", "Luke"));
        df.getData().get("col2").addAll(Arrays.asList(10.5, 20.2, 30.3));
        return df;
    }

    @Test
    public void testConstructorFromLabels() {
        DataFrame df = creer_dataframe_labels();
        List<String> labels = new ArrayList<>(Arrays.asList("col0", "col2"));
        DataFrame newDf = new DataFrame(df, labels);
        assertNotNull("le DataFrame cree ne doit pas etre null",newDf);
    }

    public void testConstructorFromLabels1() {
        DataFrame df = creer_dataframe_labels();
        List<String> labels = new ArrayList<>(Arrays.asList("col0", "col2"));
        DataFrame newDf = new DataFrame(df, labels);
        assertTrue("La nouvelle colonne a le bon label que la colonne source recuperee par son label",newDf.getData().containsKey("col0"));
    }

    public void testConstructorFromLabels2() {
        DataFrame df = creer_dataframe_labels();
        List<String> labels = new ArrayList<>(Arrays.asList("col0", "col2"));
        DataFrame newDf = new DataFrame(df, labels);
        assertTrue("La nouvelle colonne a le bon label que la colonne source recuperee par son label",newDf.getData().containsKey("col2"));
    }

    public void testConstructorFromLabels3() {
        DataFrame df = creer_dataframe_labels();
        List<String> labels = new ArrayList<>(Arrays.asList("col0", "col2"));
        DataFrame newDf = new DataFrame(df, labels);
        assertFalse("La nouvelle colonne a le bon label que la colonne source recuperee par son label",newDf.getData().containsKey("col1"));
    }

    @Test
    public void testConstructorFromLabels4() {
        DataFrame df = creer_dataframe_labels();
        List<String> labels = new ArrayList<>(Arrays.asList("col0", "col2"));
        DataFrame newDf = new DataFrame(df, labels);
        Map<String, List<Object>> newData = newDf.getData();
        assertEquals("La colonne du nouveau DataFrame a les memes valeurs que la colonne source recuperee par son label",Arrays.asList(1, 2, 3), newData.get("col0"));
    }

    @Test
    public void testConstructorFromLabels5() {
        DataFrame df = creer_dataframe_labels();
        List<String> labels = new ArrayList<>(Arrays.asList("col0", "col2"));
        DataFrame newDf = new DataFrame(df, labels);
        Map<String, List<Object>> newData = newDf.getData();
        assertEquals("La colonne du nouveau DataFrame a les memes valeurs que la colonne source recuperee par son label",Arrays.asList(10.5, 20.2, 30.3), newData.get("col2"));
    }

    /**
     * construire un DataFrame à partir d'un fichier qui n'existe pas lève une RuntimeException
    */
    @Test(expected = RuntimeException.class)
    public void testEmptyCsvThrowsException() throws IOException {
        File emptyFile = File.createTempFile("empty", ".csv");
        emptyFile.deleteOnExit();
        new DataFrame(emptyFile.getAbsolutePath());
    }

    /**
     * construire un sous-DataFrame avec un label de colonne inexistant dans le DataFrame source lève une IllegalArgumentException
    */
    @Test(expected = IllegalArgumentException.class)
    public void testUnknownColumnInConstructor() {
        List<String> types = new ArrayList<>(Arrays.asList("int", "String"));
        DataFrame df = new DataFrame(types);
        df.getData().get("col0").add(1);
        df.getData().get("col1").add("A");
        new DataFrame(df, new ArrayList<>(Arrays.asList("col0", "invalide")));
    }

    /**
     * construire un DataFrame avec plus de lignes que le DataFrame source lève une IndexOutOfBoundsException
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsIndexInConstructor() {
        List<String> types = new ArrayList<>(Arrays.asList("int", "String"));
        DataFrame df = new DataFrame(types);
        df.getData().get("col0").add(1);
        df.getData().get("col1").add("A");
        new DataFrame(df, new int[]{0, 2});
    }
}
