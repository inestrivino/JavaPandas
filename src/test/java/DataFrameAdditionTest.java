import static org.junit.Assert.*;
import org.junit.*;
import org.JavaPandas.DataFrame;

public class DataFrameAdditionTest {
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
}
