import org.JavaPandas.DataFrame;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataFrameTest {

    @Test
    public void testDataFrameStringArrayConstructor() {
        String[] columnTypes = {"int", "String"};
        DataFrame df = new DataFrame(columnTypes);
        assertNotNull(df);
    }

    @Test
    public void testDataFrameStringConstructor() {
        String csvName = "test.csv";
        DataFrame df = new DataFrame(csvName);
        assertNotNull(df);
    }

    @Test
    public void testDataFrameDataFrameIntArrayConstructor() {
        DataFrame motherFrame = new DataFrame(new String[]{"int"});
        int[] index = {0, 1, 2};
        DataFrame df = new DataFrame(motherFrame, index);
        assertNotNull(df);
    }

    @Test
    public void testDataFrameDataFrameStringArrayConstructor() {
        DataFrame motherFrame = new DataFrame(new String[]{"int"});
        String[] labels = {"label1", "label2"};
        DataFrame df = new DataFrame(motherFrame, labels);
        assertNotNull(df);
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

