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
}
