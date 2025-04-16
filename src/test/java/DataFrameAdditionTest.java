import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.JavaPandas.DataFrame;

public class DataFrameAdditionTest {
    private DataFrame creerDataframe(){
        List<String> columnTypes = new ArrayList<>();
        columnTypes.add("int");
        columnTypes.add("String");
        return new DataFrame(columnTypes);
    }

    @Test
    public void testAddRow() {
        Object[] values = {1, 2, 3};
        DataFrame df = creerDataframe();
        assertThrows(UnsupportedOperationException.class, () -> df.addRow(values));
    }

    @Test
    public void testAddCol() {
        List<String> values = new ArrayList<>();
        DataFrame df = creerDataframe();
        assertThrows(UnsupportedOperationException.class, () -> df.addCol("newCol", "int", values));
    }
}
