import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.JavaPandas.DataFrame;
import org.junit.Test;

public class DataFrameQueryTest {
    private DataFrame creerDataframe(){
        List<String> columnTypes = new ArrayList<>(Arrays.asList("int", "int", "int"));
        return new DataFrame(columnTypes);
    }

    private DataFrame creerDataframe2(){
        List<String> columnTypes = new ArrayList<>(Arrays.asList("double", "double", "int"));
        return new DataFrame(columnTypes);
    }

    @Test (expected = IllegalArgumentException.class)
    //test to see what happens if the condition has other than 3 words
    public void testQuery1(){
        DataFrame df = creerDataframe();
        df.query("col0 < col1 col2");
    }

    @Test (expected = IllegalArgumentException.class)
    //test to see what happens if the columns do not exist
    public void testQuery2(){
        DataFrame df = creerDataframe();
        df.query("Test1 < Test2");
    }

    @Test (expected = IllegalArgumentException.class)
    //test to see what happens if the columns are the same
    public void testQuery3(){
        DataFrame df = creerDataframe();
        df.query("col0 < col0");
    }

    @Test (expected = IllegalArgumentException.class)
    //test to see what happens if one of the columns is of String type
    public void testQuery4(){
        DataFrame df = creerDataframe();
        df.addCol("col3", "String", new ArrayList<>());
        df.query("col0 < col3");
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery5(){
        DataFrame df = creerDataframe();
        String[] values = {"1", "2", "3"};
        df.addRow(values);
        String[] newvalues = {"2", "1", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 < col1");
        assertEquals(new ArrayList<>(Arrays.asList(1)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery6(){
        DataFrame df = creerDataframe();
        String[] values = {"1", "2", "3"};
        df.addRow(values);
        String[] newvalues = {"2", "1", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 > col1");
        assertEquals(new ArrayList<>(Arrays.asList(2)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery7(){
        DataFrame df = creerDataframe();
        String[] values = {"2", "2", "3"};
        df.addRow(values);
        String[] newvalues = {"2", "1", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 == col1");
        assertEquals(new ArrayList<>(Arrays.asList(2)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery8(){
        DataFrame df = creerDataframe();
        String[] values = {"1", "2", "3"};
        df.addRow(values);
        String[] newvalues = {"2", "1", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 <= col1");
        assertEquals(new ArrayList<>(Arrays.asList(1)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery9(){
        DataFrame df = creerDataframe();
        String[] values = {"1", "2", "3"};
        df.addRow(values);
        String[] newvalues = {"2", "1", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 >= col1");
        assertEquals(new ArrayList<>(Arrays.asList(2)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery10(){
        DataFrame df = creerDataframe2();
        String[] values = {"1.0", "2.0", "3"};
        df.addRow(values);
        String[] newvalues = {"2.0", "1.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 > col1");
        assertEquals(new ArrayList<>(Arrays.asList(2.0)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery11(){
        DataFrame df = creerDataframe2();
        String[] values = {"1.0", "2.0", "3"};
        df.addRow(values);
        String[] newvalues = {"2.0", "1.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 < col1");
        assertEquals(new ArrayList<>(Arrays.asList(1.0)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery12(){
        DataFrame df = creerDataframe2();
        String[] values = {"1.0", "2.0", "3"};
        df.addRow(values);
        String[] newvalues = {"2.0", "1.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 >= col1");
        assertEquals(new ArrayList<>(Arrays.asList(2.0)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery13(){
        DataFrame df = creerDataframe2();
        String[] values = {"1.0", "2.0", "3"};
        df.addRow(values);
        String[] newvalues = {"2.0", "1.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 <= col1");
        assertEquals(new ArrayList<>(Arrays.asList(1.0)), queryResult.getData().get("col0"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery14(){
        DataFrame df = creerDataframe2();
        String[] values = {"2.0", "2.0", "3"};
        df.addRow(values);
        String[] newvalues = {"1.0", "2.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col0 == col1");
        assertEquals(new ArrayList<>(Arrays.asList(2.0)), queryResult.getData().get("col0"));
    }

    @Test (expected = IllegalArgumentException.class)
    //test to see what happens when we do a query
    public void testQuery15(){
        DataFrame df = creerDataframe2();
        String[] values = {"2.0", "2.0", "3"};
        df.addRow(values);
        String[] newvalues = {"1.0", "2.0", "3"};
        df.addRow(newvalues);
        df.query("col1 == col2");
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery16(){
        DataFrame df = creerDataframe2();
        String[] values = {"2.0", "4.0", "3"};
        df.addRow(values);
        String[] newvalues = {"1.0", "2.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col1 < col2");
        assertEquals(new ArrayList<>(Arrays.asList(2.0)), queryResult.getData().get("col1"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery17(){
        DataFrame df = creerDataframe2();
        String[] values = {"2.0", "4.0", "3"};
        df.addRow(values);
        String[] newvalues = {"1.0", "2.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col1 > col2");
        assertEquals(new ArrayList<>(Arrays.asList(4.0)), queryResult.getData().get("col1"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery18(){
        DataFrame df = creerDataframe2();
        String[] values = {"2.0", "4.0", "3"};
        df.addRow(values);
        String[] newvalues = {"1.0", "2.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col1 <= col2");
        assertEquals(new ArrayList<>(Arrays.asList(2.0)), queryResult.getData().get("col1"));
    }

    @Test
    //test to see what happens when we do a query
    public void testQuery19(){
        DataFrame df = creerDataframe2();
        String[] values = {"2.0", "4.0", "3"};
        df.addRow(values);
        String[] newvalues = {"1.0", "2.0", "3"};
        df.addRow(newvalues);
        DataFrame queryResult = df.query("col1 >= col2");
        assertEquals(new ArrayList<>(Arrays.asList(4.0)), queryResult.getData().get("col1"));
    }
}
