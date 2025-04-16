import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.*;
import org.JavaPandas.DataFrame;

public class DataFrameAdditionTest {
    private DataFrame creerDataframe(){
        List<String> columnTypes = new ArrayList<>(Arrays.asList("int", "String"));
        return new DataFrame(columnTypes);
    }

    @Test
    //test to check that the new element has been added
    public void testAddRow1() {
        String[] values = {"1", "Hello"};
        DataFrame df = creerDataframe();
        df.addRow(values);

        int lengthofrow = df.getData().get("col0").size();

        assertEquals(1, df.getData().get("col0").get(lengthofrow-1));
    }

    @Test (expected = IllegalArgumentException.class)
    //test to check that the element "Hello" cannot go into column of type "int"
    public void testAddRow2() {
        String[] values = {"Hello", "Hello"};
        DataFrame df = creerDataframe();
        df.addRow(values);
    }

    @Test (expected = IllegalArgumentException.class)
    //test to check that we cannot insert 5 elements into a dataframe of 2 columns
    public void testAddRow3() {
        String[] values = {"1", "Hello", "Test1", "Test2", "Test3"};
        DataFrame df = creerDataframe();
        df.addRow(values);
    }

    @Test
    //test to guarantee the new name is in the "list of column names"
    public void testAddCol1() {
        List<String> values = new ArrayList<>(Arrays.asList("1", "2", "3"));
        DataFrame df = creerDataframe();
        df.addCol("Test", "int", values);
        assertTrue(df.getColumnNames().get(2).equals("Test"));
    }

    @Test
    //test to guarantee there are now 3 columns
    public void testAddCol2() {
        List<String> values = new ArrayList<>(Arrays.asList("1", "2", "3"));
        DataFrame df = creerDataframe();
        df.addCol("Test", "int", values);
        assertEquals(3, df.getData().size());
    }

    @Test
    public void testAddCol3() {
        List<String> values = new ArrayList<>(Arrays.asList("1", "2", "3"));
        DataFrame df = creerDataframe();
        df.addCol("Test", "int", values);
        assertEquals(1, df.getData().get("Test").get(0));
    }

    @Test (expected = NumberFormatException.class)
    //test to check when the castColumn method cannot cast the values
    public void testCastColumn1() {
        DataFrame df = creerDataframe();
        List<String> rawCol = Arrays.asList("1", "2", "invalid");
        String type = "int";

        df.addCol("Test", type, rawCol);
    }

    @Test (expected = NumberFormatException.class)
    //test to check when the castColumn method cannot cast the values
    public void testCastColumn2() {
        DataFrame df = creerDataframe();
        List<String> rawCol = Arrays.asList("1.0", "2.0", "invalid");
        String type = "double";

        df.addCol("Test", type, rawCol);
    }
}
