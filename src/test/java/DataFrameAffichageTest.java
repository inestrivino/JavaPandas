import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.JavaPandas.DataFrame;
import org.junit.Test;

public class DataFrameAffichageTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Test
    public void testShowDataFrame() {
        DataFrame df = creer_DataFrame();
        df.showDataFrame();

        // Normalize line endings to Unix style
        String actual = outContent.toString().replace("\r\n", "\n").replace("\r", "\n");

        // Define the expected output without extra tab characters at the end of each line
        String expected =
            "[col0, col1, col2]\n" +
            "1\tA\t10.1\n" +
            "2\tB\t20.2\n" +
            "3\tC\t30.3\n" +
            "4\tD\t40.4\n" +
            "5\tE\t50.5\n";

        System.out.println(expected);
        expected = outContent.toString().replace("\r\n", "\n").replace("\r", "\n");
        
        assertEquals("le dataframe entier est affiche", expected, actual);

        // Reset System.out to its original state
        System.setOut(System.out);
    }

    @Test
    public void testShowFirstLines2() { //affichage de 0 lignes
        System.setOut(new PrintStream(outContent));
        DataFrame df = creer_DataFrame();
        df.showFirstLines(0);
        String actual = outContent.toString().replace("\r\n", "\n").replace("\r", "\n");
        String expected = "[col0, col1, col2]\n";
        assertEquals("showFirstLines(0) affiche les labels des colonnes",expected, actual);
    }

    @Test
    public void testShowFirstLines3() { //affichage de plus de lignes que possible
        System.setOut(new PrintStream(outContent));
        DataFrame df = creer_DataFrame();
        df.showFirstLines(13);
        String actual = outContent.toString().replace("\r\n", "\n").replace("\r", "\n");
        String expected =
                "[col0, col1, col2]\n" +
                "1\tA\t10.1\t\n" +
                "2\tB\t20.2\t\n" +
                "3\tC\t30.3\t\n" +
                "4\tD\t40.4\t\n" +
                "5\tE\t50.5\t\n";
        assertEquals("appel a showFirstLines avec un parametre superieur au nombre total de ligne affiche tout le dataframe",expected, actual);
    }

    private DataFrame creer_DataFrame(){
        String[] types = {"int", "String", "double"};
        DataFrame df = new DataFrame(types);
        df.getData().get("col0").addAll(Arrays.asList(1, 2, 3, 4, 5));
        df.getData().get("col1").addAll(Arrays.asList("A", "B", "C", "D", "E"));
        df.getData().get("col2").addAll(Arrays.asList(10.1, 20.2, 30.3, 40.4, 50.5));
        return df;
    }

    @Test
    public void testShowLastLines() {
        System.setOut(new PrintStream(outContent));
        DataFrame df = creer_DataFrame();
        df.showLastLines(2);
        String actual = outContent.toString().replace("\r\n", "\n").replace("\r", "\n");
        String expected =
                "[col0, col1, col2]\n" +
                "4\tD\t40.4\t\n" +
                "5\tE\t50.5\t\n";
        assertEquals("les deux dernieres lignes du dataframe sont affichees",expected, actual);
    }

    @Test
    public void testShowLastLines2() { //affichage de 0 lignes
        System.setOut(new PrintStream(outContent));
        DataFrame df = creer_DataFrame();
        df.showLastLines(0);
        String actual = outContent.toString().replace("\r\n", "\n").replace("\r", "\n");
        String expected = "[col0, col1, col2]\n";
        assertEquals("showLastLines(0) affiche les labels des colonnes",expected, actual);
    }

    public void testShowLastLines3() { //affichage de plus de lignes que possible
        System.setOut(new PrintStream(outContent));
        DataFrame df = creer_DataFrame();
        df.showLastLines(13);
        String actual = outContent.toString().replace("\r\n", "\n").replace("\r", "\n");
        String expected =
                "[col0, col1, col2]\n" +
                "1\tA\t10.1\t\n" +
                "2\tB\t20.2\t\n" +
                "3\tC\t30.3\t\n" +
                "4\tD\t40.4\t\n" +
                "5\tE\t50.5\t\n";
        assertEquals("appel a showLastLines avec un parametre superieur au nombre total de ligne affiche tout le dataframe",expected, actual);
    }
}
