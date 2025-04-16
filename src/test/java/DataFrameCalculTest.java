import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;

import org.JavaPandas.DataFrame;
import org.junit.Test;

public class DataFrameCalculTest {

    private DataFrame creer_DataFrame(){
        String[] types = {"int", "double", "String"};
        DataFrame df = new DataFrame(types);
        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3));
        df.getData().get("col1").addAll(Arrays.asList(1.5, 2.5, 3.5));
        df.getData().get("col2").addAll(Arrays.asList("A", "B", "C"));
        return df;
    }

    //sum

    @Test
    public void testSumIntColumn() {
        DataFrame df = creer_DataFrame();
        assertEquals("la somme de 1, 2, 3 doit valoir 6.0",6.0, df.sum("col0"),0);
    }

    @Test
    public void testSumDoubleColumn() {
        DataFrame df = creer_DataFrame();
        assertEquals("la somme de 1.5, 2.5, 3.5 doit valoir 7.5",7.5, df.sum("col1"),0);
    }

    //appeler sum sur une colonne d'un type non numérique renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testSumStringColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.sum("col2");
    }

    //appeler sum sur une colonne qui n'existe pas doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testSumUnknownColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.sum("unknown");
    }

    @Test
    public void testSumEmptyColumn() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        assertEquals("la somme d'une colonne vide doit valoir 0.0",0.0, df.sum("col0"), 0.0001);
    }

    @Test
    public void testSumNegativeAndPositiveNumbers() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5, -10, 15));
        assertEquals("la somme de -5, -10, 15 doit valoir 0.0",0.0,df.sum("col0"),0);
    }

    @Test
    public void testSumNegativeNumbers() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5, -10, -15));
        assertEquals("la somme de -5, -10, -15 doit valoir -30.0",-30.0,df.sum("col0"),0);
    }

    @Test
    public void testSumNegativeNumbersDouble() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.5, -10.5, -15.5));
        assertEquals("la somme de -5, -10, -15 doit valoir -30.0",-31.5,df.sum("col0"),0);
    }

    @Test
    public void testSumWithLargeNumbers() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(1000000, 2000000, 3000000));
        assertEquals("la somme de 1000000, 2000000, 3000000 doit valoir 6000000",6000000,df.sum("col0"),0);
    }

    //une RuntimeException doit être levée lorsqu'un constructeur de DataFrame est appelé avec un fichier qui n'existe pas
    @Test(expected = RuntimeException.class)
    public void testIOExceptionWhenFileDoesNotExist() {
        new DataFrame("fichier_inexistant.csv");
    }

    //mean

    @Test
    public void testMeanIntColumn() {
        DataFrame df = creer_DataFrame();
        double mean = df.mean("col0");
        assertEquals("la moyenne de 1, 2, 3 doit valoir 2.0",2.0, mean, 0.0001);
    }

    @Test
    public void testMeanDoubleColumn() {
        DataFrame df = creer_DataFrame();
        double mean = df.mean("col1"); 
        assertEquals("la moyenne de 1.5, 2.5, 3.5 doit valoir 2.5",2.5, mean, 0.0001);
    }

    //appeler mean sur une colonne d'un type non numérique doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMeanOnStringColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.mean("col2");
    }

    //appeler mean sur une colonne qui n'existe pas doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMeanOnUnknownColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.mean("doesNotExist");
    }

    @Test
    public void testMeanEmptyColumnReturnsZero() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        assertEquals("la moyenne d'une colonne vide doit valoir 0.0",0.0, df.mean("col0"), 0.0001);
    }

    @Test
    public void testMeanNegativeAndPositiveNumbers() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5, -10, 15));
        assertEquals("la moyenne de -5, -10, 15 doit valoir 0.0",0.0, df.mean("col0"), 0.0001);
    }

    @Test
    public void testMeanNegativeNumbers() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5, -10, -15));
        assertEquals("la moyenne de -5, -10, -15 doit valoir -10.0",-10.0, df.mean("col0"), 0.0001);
    }

    @Test
    public void testMeanWithLargeValues() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(1000000, 2000000, 3000000));
        assertEquals("la moyenne de 1000000, 2000000, 3000000 doit valoir 2000000.0",2000000.0, df.mean("col0"), 0.0001);
    }

    //cumsum

    @Test
    public void testCumsumIntColumn() {
        DataFrame df = creer_DataFrame();
        List<Object> expected = Arrays.asList(1.0, 3.0, 6.0);
        assertEquals("cumsum sur une colonne contenant 1.0, 3.0, 6.0 doit renvoyer un Dataframe dont la colonne contient 1.0, 3.0, 6.0",expected, df.cumsum("col0").getData().get("col0"));
    }

    @Test
    public void testCumsumDoubleColumn() {
        DataFrame df = creer_DataFrame();
        List<Object> expected = Arrays.asList(1.5, 4.0, 7.5);
        assertEquals("cumsum sur une colonne contenant 1.5, 2.5, 3.5 doit renvoyer un Dataframe dont la colonne contient 1.5, 4.0, 7.5",expected, df.cumsum("col1").getData().get("col1"));
    }

    @Test
    public void testCumsumNegativeAndPositive() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.0, -10.0, 15.0));
        List<Object> expected = Arrays.asList(-5.0, -15.0, 0.0);
        assertEquals("cumsum sur une colonne contenant -5.0, -10.0, 15.0 doit renvoyer un Dataframe dont la colonne contient -5.0, -15.0, 0.0",expected, df.cumsum("col0").getData().get("col0"));
    }

    @Test
    public void testCumsumNegative() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.0, -10.0, -15.0));
        List<Object> expected = Arrays.asList(-5.0, -15.0, -30.0);
        assertEquals("cumsum sur une colonne contenant -5.0, -10.0, -15.0 doit renvoyer un Dataframe dont la colonne contient -5.0, -15.0, -30.0",expected, df.cumsum("col0").getData().get("col0"));
    }

    //appeler cumsum sur une colonne d'un type non numérique doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testCumsumStringColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.cumsum("col2");
    }

    //appeler cumsum sur une colonne qui n'existe pas doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testCumsumOnNonExistentColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.cumsum("unknownCol");
    }

    @Test
    public void testCumsumEmptyColumn() {
        DataFrame emptyDf = new DataFrame(new String[] {"int"});
        DataFrame result = emptyDf.cumsum("col0");
        assertTrue(result.getData().get("col0").isEmpty());
    }

    @Test
    public void testCumsumEmptyColumn2() {
        DataFrame emptyDf = new DataFrame(new String[] {"int"});
        List<Object> expected = Arrays.asList();
        assertEquals("cumsum sur une colonne vide doit renvoyer un Dataframe dont la colonne est vide",expected, emptyDf.cumsum("col0").getData().get("col0"));
    }

    //cumprod

    @Test
    public void testCumprodIntColumn() {
        DataFrame df = creer_DataFrame();
        List<Object> expected = Arrays.asList(1.0, 2.0, 6.0);
        assertEquals("cumprod sur une colonne contenant 1.0, 3.0, 6.0 doit renvoyer un Dataframe dont la colonne contient 1.0, 3.0, 6.0",expected, df.cumprod("col0").getData().get("col0"));
    }

    @Test
    public void testCumprodIntZeroColumn() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(1.0, 2.0, 0.0,17.0));
        List<Object> expected = Arrays.asList(1.0, 2.0, 0.0,0.0);
        assertEquals("cumprod sur une colonne contenant 1.0, 2.0, 0.0, 17.0 doit renvoyer un Dataframe dont la colonne contient 1.0, 2.0, 0.0, 0.0",expected, df.cumprod("col0").getData().get("col0"));
    }

    @Test
    public void testCumprodDoubleColumn() {
        DataFrame df = creer_DataFrame();
        List<Object> expected = Arrays.asList(1.5, 3.75, 13.125);
        assertEquals("cumprod sur une colonne contenant 1.5, 2.5, 3.5 doit renvoyer un Dataframe dont la colonne contient 1.5, 3.75, 5.625",expected, df.cumprod("col1").getData().get("col1"));
    }

    @Test
    public void testCumprodDoubleZeroColumn() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(1.5, 2.5, 0.0, 17.5));
        List<Object> expected = Arrays.asList(1.5, 3.75, 0.0,0.0);
        assertEquals("cumprod sur une colonne contenant 1.5, 2.5, 0.0, 17.5 doit renvoyer un Dataframe dont la colonne contient 1.5, 3.75, 0.0,0.0",expected, df.cumprod("col0").getData().get("col0"));
    }

    @Test
    public void testCumprodNegativeAndPositive() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.0, -10.0, 15.0));
        List<Object> expected = Arrays.asList(-5.0, 50.0, 750.0);
        assertEquals("cumprod sur une colonne contenant -5.0, -10.0, 15.0 doit renvoyer un Dataframe dont la colonne contient -5.0, 50.0, 750.0",expected, df.cumprod("col0").getData().get("col0"));
    }

    @Test
    public void testCumprodNegative() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.0, -10.0, -15.0));
        List<Object> expected = Arrays.asList(-5.0, 50.0, -750.0);
        assertEquals("cumprod sur une colonne contenant -5.0, -10.0, -15.0 doit renvoyer un Dataframe dont la colonne contient -5.0, 50.0, -750.0",expected, df.cumprod("col0").getData().get("col0"));
    }

    //appeler cumprod sur une colonne d'un type non numérique doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testCumprodStringColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.cumprod("col2");
    }

    //appeler cumprod sur une colonne qui n'existe pas doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testCumprodOnNonExistentColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.cumprod("unknownCol");
    }

    @Test
    public void testCumprodEmptyColumn() {
        DataFrame emptyDf = new DataFrame(new String[] {"int"});
        DataFrame result = emptyDf.cumprod("col0");
        assertTrue(result.getData().get("col0").isEmpty());
    }

    @Test
    public void testCumprodEmptyColumn2() {
        DataFrame emptyDf = new DataFrame(new String[] {"int"});
        List<Object> expected = Arrays.asList();
        assertEquals("cumprod sur une colonne vide doit renvoyer un Dataframe dont la colonne est vide",expected, emptyDf.cumprod("col0").getData().get("col0"));
    }

    //max

    @Test
    public void testMax1(){
        DataFrame df = creer_DataFrame();
        assertEquals("le max de 1 2 3 est 3",3, df.max("col0"),0);
    }

    @Test
    public void testMax2(){
        DataFrame df = creer_DataFrame();
        assertEquals("le max de 1.5, 2.5, 3.5 est 3.5",3.5, df.max("col1"),0);
    }

    @Test
    public void testMax3(){
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.0, -10.0, -15.0));
        assertEquals("le max de -5.0, -10.0, -15.0",-5.0, df.max("col0"),0);
    }

    @Test
    public void testMax4(){
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.0, -10.0, 15.0));
        assertEquals("le max de -5.0, -10.0, 15.0",15.0, df.max("col0"),0);
    }

    //appeler max sur une colonne d'un type non numérique doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMax5() {
        DataFrame df = creer_DataFrame();
        df.max("col2");
    }

    //appeler max sur une colonne qui n'existe pas doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMax6() {
        DataFrame df = creer_DataFrame();
        df.max("inexistant");
    }

    //appeler max sur une colonne vide doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMax7() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.max("col0");
    }

    //min

    @Test
    public void testMin1(){
        DataFrame df = creer_DataFrame();
        assertEquals("le min de 1, 2, 3 est 1",1, df.min("col0"),0);
    }

    @Test
    public void testMin2(){
        DataFrame df = creer_DataFrame();
        assertEquals("le min de 1.5, 2.5, 3.5 est 3.5 est 1.5",1.5, df.min("col1"),0);
    }

    @Test
    public void testMin3(){
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.0, -10.0, -15.0));
        assertEquals("le min de -5.0, -10.0, -15.0 est -15.0",-15.0, df.min("col0"),0);
    }

    @Test
    public void testMin4(){
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.getData().get("col0").addAll(Arrays.asList(-5.0, -10.0, 15.0));
        assertEquals("le min de -5.0, -10.0, 15.0 est -10.0",-10.0, df.min("col0"),0);
    }

    //appeler min sur une colonne d'un type non numérique doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMin5() {
        DataFrame df = creer_DataFrame();
        df.min("col2");
    }

    //appeler min sur une colonne qui n'existe pas doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMin6() {
        DataFrame df = creer_DataFrame();
        df.min("inexistant");
    }

    //appeler min sur une colonne vide doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMin7() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        df.min("col0");
    }

}
