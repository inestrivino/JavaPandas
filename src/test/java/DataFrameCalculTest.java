import static org.junit.Assert.assertEquals;
import java.util.Arrays;
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

    //la somme d'une colonne d'un type non numérique doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testSumStringColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.sum("col2");
    }

    //la somme d'une colonne inexistante doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testSumUnknownColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.sum("unknown");
    }

    @Test
    public void testSumEmptyColumn() {
        DataFrame df = creer_DataFrame();
        df.getData().get("col0").clear();
        assertEquals("la sonne d'une colonne vide doit valoir 0.0",0.0, df.sum("col0"), 0.0001);
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

    //la moyenne d'une colonne d'un type non numérique doit renvoyer une IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testMeanOnStringColumnThrowsException() {
        DataFrame df = creer_DataFrame();
        df.mean("col2");
    }

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

    @Test
    public void testQuantile() {
        DataFrame df = new DataFrame(new String[]{"int"});
        //assertThrows(UnsupportedOperationException.class, () -> df.quantile("label"));
    }

    @Test
    public void testCumsum() {
        DataFrame df = new DataFrame(new String[]{"int"});
        //assertThrows(UnsupportedOperationException.class, () -> df.cumsum("label"));
    }

    @Test
    public void testCumprod() {
        DataFrame df = new DataFrame(new String[]{"int"});
        //assertThrows(UnsupportedOperationException.class, () -> df.cumprod("label"));
    }
}
